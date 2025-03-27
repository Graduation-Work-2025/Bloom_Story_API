package bloom_story.domain.friendship.dto;

import static com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonNaming;

import bloom_story.domain.user.model.User;
import io.swagger.v3.oas.annotations.media.Schema;

@JsonNaming(value = SnakeCaseStrategy.class)
public record SearchFriendResponse(
    @Schema(description = "검색된 친구 목록", requiredMode = REQUIRED)
    List<InnerFriendResponse> friends
) {

    public static SearchFriendResponse from(List<User> friends) {
        return new SearchFriendResponse(
            friends.stream().map(InnerFriendResponse::from).toList()
        );
    }

    @JsonNaming(value = SnakeCaseStrategy.class)
    private record InnerFriendResponse(
        @Schema(description = "상대 id", example = "1", requiredMode = REQUIRED)
        String userId,

        @Schema(description = "상대 이름", example = "1", requiredMode = REQUIRED)
        String name,

        @Schema(description = "상대와의 친구 여부", example = "1", requiredMode = REQUIRED)
        Boolean isFriend
    ) {

        public static InnerFriendResponse from(User user) {
            return new InnerFriendResponse(
                user.getUserId(),
                user.getName(),
                false
            );
        }
    }
}
