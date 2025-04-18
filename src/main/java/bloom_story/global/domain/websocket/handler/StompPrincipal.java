package bloom_story.global.domain.websocket.handler;

import java.security.Principal;

class StompPrincipal implements Principal {

    String name;

    StompPrincipal(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }
}