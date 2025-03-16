package bloom_story.domain.friendship.dto;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;

public record FriendshipRequest(
    @JsonProperty("friend_id")
    @Schema(description = "상대 id", example = "1", requiredMode = REQUIRED)
    Integer friendId
) {

}
