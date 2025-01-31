package bloom_story.global.domain.websocket.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

// 메시지 DTO 클래스 (JSON 변환용)
public record UnityMessage(
    @JsonProperty("command") String command,
    @JsonProperty("data") String data,
    @JsonProperty("response") String response
) {

    public static UnityMessage fromJson(String json) {
        return null;
    }
}

