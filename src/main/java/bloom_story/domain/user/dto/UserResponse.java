package bloom_story.domain.user.dto;

import static com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;

import com.fasterxml.jackson.databind.annotation.JsonNaming;

import bloom_story.domain.user.model.User;
import io.swagger.v3.oas.annotations.media.Schema;

@JsonNaming(value = SnakeCaseStrategy.class)
public record UserResponse(
    @Schema(description = "고유번호", example = "1")
    Integer id,

    @Schema(description = "성명", example = "황현식")
    String name,

    @Schema(description = "닉네임", example = "캔따개")
    String nickname,

    @Schema(description = "아이디", example = "hyunn815")
    String userId,

    @Schema(description = "휴대폰 번호", example = "010-8434-1160")
    String phone
) {

    public static UserResponse from(User user) {
        return new UserResponse(
            user.getId(),
            user.getName(),
            user.getNickname(),
            user.getUserId(),
            user.getPhone()
        );
    }
}
