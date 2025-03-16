package bloom_story.global.domain.websocket.handler;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.fasterxml.jackson.databind.ObjectMapper;

import bloom_story.domain.user.controller.UserHandler;
import bloom_story.global.domain.jwt.JwtProvider;
import bloom_story.global.domain.websocket.dto.WebSocketRequest;
import bloom_story.global.domain.websocket.dto.WebSocketResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class UnityWebSocketHandler extends TextWebSocketHandler {

    private final List<WebSocketHandler> webSocketHandlers;
    private final CopyOnWriteArrayList<WebSocketSession> sessions = new CopyOnWriteArrayList<>();
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final UserHandler userHandler;
    private final JwtProvider jwtProvider;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessions.add(session);
        log.info("[WebSocket] 연결 성공: " + session.getId());
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage textMessage) throws Exception {
        log.info("[WebSocket] 메시지 수신: " + textMessage.getPayload());

        WebSocketRequest request = objectMapper.readValue(textMessage.getPayload(), WebSocketRequest.class);
        Integer userId = null;
        try {
            userId = jwtProvider.getUserId(request.getToken());
        } catch (Exception e) {

        }
        String domain = request.getDomain();

        for (var webSocketHandler : webSocketHandlers) {
            if (webSocketHandler.is_supported(domain)) {
                WebSocketResponse<?> response = webSocketHandler.handle(session, userId, request);
                sendMessage(session, response);
                return;
            }
        }

        session.sendMessage(new TextMessage("{\"error\": \"존재하지 않는 도메인\"}"));
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        sessions.remove(session);
        log.info("[WebSocket] 연결 종료: " + session.getId());
    }

    private void sendMessage(WebSocketSession session, WebSocketResponse message) throws IOException {
        String response = objectMapper.writeValueAsString(message);
        session.sendMessage(new TextMessage(response));
    }
}
