package bloom_story.domain.user.dto;

import static com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.NOT_REQUIRED;
import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

import com.fasterxml.jackson.databind.annotation.JsonNaming;

import io.swagger.v3.oas.annotations.media.Schema;

@JsonNaming(value = SnakeCaseStrategy.class)
public record UserSignupRequest(
    @Schema(description = "성명", example = "황현식", requiredMode = REQUIRED)
    String name,

    @Schema(description = "닉네임", example = "캔따개", requiredMode = REQUIRED)
    String nickname,

    @Schema(description = "아이디", example = "hyunn815", requiredMode = REQUIRED)
    String userId,

    @Schema(description = "비밀번호", example = "qwer1234", requiredMode = REQUIRED)
    String password,

    @Schema(description = "휴대폰 번호", example = "010-8434-1160", requiredMode = NOT_REQUIRED)
    String phone,

    @Schema(description = "캐릭터 번호", example = "1", requiredMode = REQUIRED)
    Integer characterId
) {

}
