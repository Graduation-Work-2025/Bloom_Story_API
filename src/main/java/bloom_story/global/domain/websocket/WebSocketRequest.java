package bloom_story.global.domain.websocket;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
public class WebSocketRequest {

    @JsonProperty("domain")
    private String domain;

    @JsonProperty("command")
    private String command;

    @JsonProperty("request")
    private Object request;

    @Builder
    private WebSocketRequest(
        String domain,
        String command,
        String request
    ) {
        this.domain = domain;
        this.command = command;
        this.request = request;
    }
}
