package bloom_story.global.domain.websocket.handler;

import org.springframework.web.socket.WebSocketSession;

import bloom_story.global.domain.websocket.dto.WebSocketRequest;
import bloom_story.global.domain.websocket.dto.WebSocketResponse;

public interface WebSocketHandler {

    boolean is_supported(String command); // 해당 핸들러가 특정 command를 처리하는지 여부

    WebSocketResponse handle(WebSocketSession session, Integer userId, WebSocketRequest message) throws Exception;
}

