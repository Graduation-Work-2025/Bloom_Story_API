package bloom_story.domain.user.dto;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;

public record UserLoginRequest(
    @JsonProperty("email")
    @Schema(description = "이메일", example = "hyunn815@naver.com", requiredMode = REQUIRED)
    String email,

    @JsonProperty("password")
    @Schema(description = "비밀번호", example = "qwer1234", requiredMode = REQUIRED)
    String password
) {

}
