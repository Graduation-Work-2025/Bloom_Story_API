package bloom_story.global.domain.websocket.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import bloom_story.global.domain.websocket.model.ErrorCode;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
public class WebSocketResponse<T> {

    @JsonProperty("error")
    private ErrorCode error;

    @JsonProperty("response")
    private T response;

    @Builder
    private WebSocketResponse(
        ErrorCode error,
        T response
    ) {
        this.error = error;
        this.response = response;
    }

    public static <T> WebSocketResponse<T> of(ErrorCode error, T response) {
        return new WebSocketResponse<>(
            error,
            response
        );
    }
}

