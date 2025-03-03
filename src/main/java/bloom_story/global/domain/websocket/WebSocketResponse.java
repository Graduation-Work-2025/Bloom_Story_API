package bloom_story.global.domain.websocket;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
public class WebSocketResponse<T> {

    @JsonProperty("error_code")
    private Integer errorCode;

    @JsonProperty("domain")
    private String domain;

    @JsonProperty("command")
    private String command;

    @JsonProperty("request")
    private Object request;

    @JsonProperty("response")
    private T response;

    @Builder
    private WebSocketResponse(
        Integer errorCode,
        String domain,
        String command,
        Object request,
        T response
    ) {
        this.errorCode = errorCode;
        this.domain = domain;
        this.command = command;
        this.request = request;
        this.response = response;
    }

    public static <T> WebSocketResponse<T> of(Integer errorCode, WebSocketRequest request, T response) {
        return new WebSocketResponse<>(
            errorCode,
            request.getDomain(),
            request.getCommand(),
            request.getRequest(),
            response
        );
    }
}

