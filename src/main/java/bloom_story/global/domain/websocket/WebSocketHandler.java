package bloom_story.global.domain.websocket;

import org.springframework.web.socket.WebSocketSession;

public interface WebSocketHandler {

    boolean is_supported(String command); // 해당 핸들러가 특정 command를 처리하는지 여부

    WebSocketResponse handle(WebSocketSession session, WebSocketRequest message) throws Exception;
}

