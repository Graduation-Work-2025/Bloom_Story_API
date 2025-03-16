package bloom_story.global.domain.websocket.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ErrorCode {

    @JsonProperty("code")
    private Integer errorCode;

    @JsonProperty("message")
    private String errorMessage;

    @Builder
    public ErrorCode(Integer errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }
}
