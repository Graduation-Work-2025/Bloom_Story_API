package bloom_story.domain.user.dto;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

import io.swagger.v3.oas.annotations.media.Schema;

public record UserLoginResponse(
    @Schema(description = "엑세스 토큰", example = "ejoiug3yhj67ki6l5j4hgfho3i", requiredMode = REQUIRED)
    String accessToken

    // @Schema(description = "닉네임", example = "캔따개", requiredMode = REQUIRED)
    // String refreshToken
    ) {

    public static UserLoginResponse of(String accessToken) {
        return new UserLoginResponse(
            accessToken
        );
    }
}
