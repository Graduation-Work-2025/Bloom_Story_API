package bloom_story.domain.friendship.dto;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

import java.util.List;

import bloom_story.domain.user.model.User;
import io.swagger.v3.oas.annotations.media.Schema;

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

    private record InnerFriendshipResponse(
        @Schema(description = "친구 고유번호", example = "1", requiredMode = REQUIRED)
        Integer id,

        @Schema(description = "친구 메일", example = "hyunn815@naver.com")
        String email
    ) {
        public static InnerFriendshipResponse from(User user) {
            return new InnerFriendshipResponse(
                user.getId(),
                user.getUserId()
            );
        }
    }
}
