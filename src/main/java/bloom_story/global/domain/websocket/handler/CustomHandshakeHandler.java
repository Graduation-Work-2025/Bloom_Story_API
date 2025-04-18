package bloom_story.global.domain.websocket.handler;

import java.security.Principal;
import java.util.Map;
import java.util.UUID;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

public class CustomHandshakeHandler extends DefaultHandshakeHandler {

    protected Principal determineUser(
        ServerHttpRequest request,
        WebSocketHandler wsHandler,
        Map<String, Object> attributes
    ) {
        Object userId = attributes.get("userId");

        // 세션에 userId가 저장되어 있어야 합니다
        if (userId != null) {
            return new StompPrincipal(userId.toString());
        }

        return new StompPrincipal("1");
    }
}