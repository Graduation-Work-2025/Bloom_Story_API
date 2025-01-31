package bloom_story.global.domain.websocket.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArrayList;

import bloom_story.global.domain.websocket.dto.UnityMessage;

public class UnityWebSocketHandler extends TextWebSocketHandler {

    private static final CopyOnWriteArrayList<WebSocketSession> sessions = new CopyOnWriteArrayList<>();
    private final ObjectMapper objectMapper = new ObjectMapper(); // JSON 변환 객체

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessions.add(session);
        System.out.println("Unity WebSocket 연결됨: " + session.getId());
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        System.out.println("Unity로부터 메시지 수신: " + message.getPayload());

        // JSON 데이터 변환 (예제)
        UnityMessage unityMessage = objectMapper.readValue(message.getPayload(), UnityMessage.class);
        unityMessage.setResponse("서버에서 응답함");

        // Unity에 메시지 응답
        sendMessage(session, unityMessage);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        sessions.remove(session);
        System.out.println("Unity WebSocket 연결 종료: " + session.getId());
    }

    private void sendMessage(WebSocketSession session, UnityMessage message) throws IOException {
        String response = objectMapper.writeValueAsString(message);
        session.sendMessage(new TextMessage(response));
    }
}
