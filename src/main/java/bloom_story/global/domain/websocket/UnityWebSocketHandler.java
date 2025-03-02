package bloom_story.global.domain.websocket;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArrayList;

import bloom_story.domain.comunity.story.dto.StoryResponse;
import bloom_story.domain.comunity.story.service.StoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class UnityWebSocketHandler extends TextWebSocketHandler {

    private static final CopyOnWriteArrayList<WebSocketSession> sessions = new CopyOnWriteArrayList<>();
    private final ObjectMapper objectMapper = new ObjectMapper(); // JSON 변환 객체

    private final StoryService storyService;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessions.add(session);
        log.info("[WebSocket] 연결 성공: " + session.getId());
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        log.info("[WebSocket] 메시지 수신: " + message.getPayload());

        // JSON 데이터 변환 (예제)
        UnityMessage unityMessage = objectMapper.readValue(message.getPayload(), UnityMessage.class);
        String command = unityMessage.command();

        if (command.equals("get_story")) {
            Integer storyId = Integer.parseInt(unityMessage.data());
            StoryResponse story = storyService.getStoryById(storyId);
            UnityMessage response = new UnityMessage(
                "response",
                story.content(),
                "스토리 내용"
            );
            sendMessage(session, response);
        }

        sendMessage(session, unityMessage);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        sessions.remove(session);
        log.info("[WebSocket] 연결 종료: " + session.getId());
    }

    private void sendMessage(WebSocketSession session, UnityMessage message) throws IOException {
        String response = objectMapper.writeValueAsString(message);
        session.sendMessage(new TextMessage(response));
    }
}
