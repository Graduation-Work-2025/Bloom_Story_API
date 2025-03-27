package bloom_story.domain.friendship.dto;

import static com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;

import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(value = SnakeCaseStrategy.class)
public record FriendshipResponse(

) {
}
