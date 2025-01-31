package bloom_story.domain.user.dto;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.NOT_REQUIRED;
import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

import bloom_story.domain.user.model.User;
import io.swagger.v3.oas.annotations.media.Schema;

public record UserLoginResponse(
    @Schema(description = "성명", example = "황현식", requiredMode = REQUIRED)
    String name,

    @Schema(description = "닉네임", example = "캔따개", requiredMode = REQUIRED)
    String nickname,

    @Schema(description = "이메일", example = "hyunn815@naver.com", requiredMode = REQUIRED)
    String email,

    @Schema(description = "휴대폰 번호", example = "010-8434-1160", requiredMode = NOT_REQUIRED)
    String phone
) {

    public static UserLoginResponse from(UserSignupRequest request) {
        return new UserLoginResponse(
            request.name(),
            request.nickname(),
            request.email(),
            request.phone()
        );
    }

    public static UserLoginResponse fromByUser(User user) {
        return new UserLoginResponse(
            user.getName(),
            user.getNickname(),
            user.getEmail(),
            user.getPhone()
        );
    }
}
