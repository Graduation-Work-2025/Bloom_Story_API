package bloom_story.domain.comunity.story.dto;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

import java.util.List;

import org.locationtech.jts.geom.Point;

import bloom_story.domain.comunity.story.model.Story;
import io.swagger.v3.oas.annotations.media.Schema;

public record StoriesResponse(
    List<InnerStoryResponse> stories
) {

    public static StoriesResponse from(List<Story> stories) {
        return new StoriesResponse(
            stories.stream()
                .map(InnerStoryResponse::from)
                .toList()
        );
    }

    private record InnerStoryResponse(
        @Schema(description = "스토리 고유번호", example = "1", requiredMode = REQUIRED)
        Integer id,

        @Schema(description = "스토리 제목", example = "2공에서 있었던 일", requiredMode = REQUIRED)
        String title,

        @Schema(description = "위치 정보 (POINT 형식)", example = "POINT(37.7749 -122.4194)", requiredMode = REQUIRED)
        Point location,

        @Schema(description = "작성자 ID", example = "1", requiredMode = REQUIRED)
        Integer userId,

        @Schema(description = "감정 ID", example = "1", requiredMode = REQUIRED)
        Integer emotionId,

        @Schema(description = "Bloom ID", example = "1", requiredMode = REQUIRED)
        Integer bloomId
    ) {

        private static InnerStoryResponse from(Story story) {
            return new InnerStoryResponse(
                story.getId(),
                story.getTitle(),
                story.getLocation(),
                story.getUser().getId(),
                story.getEmotion().getId(),
                story.getBloom().getId()
            );
        }

    }
}
