package bloom_story.domain.story.dto;

import static com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.NOT_REQUIRED;
import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonNaming;

import bloom_story.domain.location.service.LocationService;
import bloom_story.domain.story.model.Story;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@JsonNaming(value = SnakeCaseStrategy.class)
public record StoryGardenResponse(
    List<InnerStoryResponse> stories
) {

    public static StoryGardenResponse from(List<Story> stories) {
        return new StoryGardenResponse(
            stories.stream()
                .map(InnerStoryResponse::from)
                .toList()
        );
    }

    @JsonNaming(value = SnakeCaseStrategy.class)
    private record InnerStoryResponse(
        @Schema(description = "스토리 고유번호", example = "1", requiredMode = REQUIRED)
        Integer id,

        @Schema(description = "이미지 url", requiredMode = NOT_REQUIRED)
        String imageUrl
    ) {

        private static InnerStoryResponse from(Story story) {
            return new InnerStoryResponse(
                story.getId(),
                story.getImageUrl() == null ? null : story.getImageUrl()
            );
        }

    }
}
