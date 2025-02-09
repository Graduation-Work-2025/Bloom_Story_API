package bloom_story.domain.user.dto;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.NOT_REQUIRED;
import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

import io.swagger.v3.oas.annotations.media.Schema;

public record UserSignupRequest(

    @Schema(description = "성명", example = "황현식", requiredMode = REQUIRED)
    String name,

    @Schema(description = "닉네임", example = "캔따개", requiredMode = REQUIRED)
    String nickname,

    @Schema(description = "이메일", example = "hyunn815@naver.com", requiredMode = REQUIRED)
    String email,

    @Schema(description = "비밀번호", example = "qwer1234", requiredMode = REQUIRED)
    String password,

    @Schema(description = "휴대폰 번호", example = "010-8434-1160", requiredMode = NOT_REQUIRED)
    String phone
) {

}
