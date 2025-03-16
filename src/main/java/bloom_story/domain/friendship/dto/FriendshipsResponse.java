package bloom_story.domain.friendship.dto;

import static com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonNaming;

import bloom_story.domain.user.model.User;
import io.swagger.v3.oas.annotations.media.Schema;

@JsonNaming(value = SnakeCaseStrategy.class)
public record FriendshipsResponse(
    @Schema(description = "스토리 고유번호", example = "1", requiredMode = REQUIRED)
    List<InnerFriendshipResponse> friendships
) {

    public static FriendshipsResponse from(List<User> friends) {
        return new FriendshipsResponse(
            friends.stream()
                .map(InnerFriendshipResponse::from)
                .toList()
        );
    }

    @JsonNaming(value = SnakeCaseStrategy.class)
    private record InnerFriendshipResponse(
        @Schema(description = "친구 고유번호", example = "1", requiredMode = REQUIRED)
        Integer id,

        @Schema(description = "친구 아이디", example = "hyunn815")
        String user_id
    ) {
        public static InnerFriendshipResponse from(User user) {
            return new InnerFriendshipResponse(
                user.getId(),
                user.getUserId()
            );
        }
    }
}
