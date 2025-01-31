package bloom_story.domain.user.dto;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

import io.swagger.v3.oas.annotations.media.Schema;

public record UserLoginRequest(
    @Schema(description = "이메일", example = "hyunn815@naver.com", requiredMode = REQUIRED)
    String email,

    @Schema(description = "비밀번호", example = "qwer1234", requiredMode = REQUIRED)
    String password
) {
}
