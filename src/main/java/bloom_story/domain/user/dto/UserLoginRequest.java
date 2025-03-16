package bloom_story.domain.user.dto;

import static com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

import com.fasterxml.jackson.databind.annotation.JsonNaming;

import io.swagger.v3.oas.annotations.media.Schema;

@JsonNaming(value = SnakeCaseStrategy.class)
public record UserLoginRequest(
    @Schema(description = "아이디", example = "hyunn815", requiredMode = REQUIRED)
    String userId,

    @Schema(description = "비밀번호", example = "qwer1234", requiredMode = REQUIRED)
    String password
) {

}
