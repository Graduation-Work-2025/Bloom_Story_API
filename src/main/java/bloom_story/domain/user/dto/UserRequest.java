package bloom_story.domain.user.dto;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;

public record UserRequest(
    @JsonProperty("user_id")
    @Schema(description = "사용자 id", example = "1", requiredMode = REQUIRED)
    Integer userId
) {

}
