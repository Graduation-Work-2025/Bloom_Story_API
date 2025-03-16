package bloom_story.global.domain.websocket.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class WebSocketRequest {

    @JsonProperty("domain")
    private String domain;

    @JsonProperty("command")
    private String command;

    @JsonProperty("token")
    private String token;

    @JsonProperty("request")
    private Object request;

    @Builder
    private WebSocketRequest(
        String domain,
        String command,
        String token,
        String request
    ) {
        this.domain = domain;
        this.command = command;
        this.token = token;
        this.request = request;
    }
}
