package bloom_story.domain.comunity.story.dto;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

import java.util.List;

import org.locationtech.jts.geom.Point;

import bloom_story.domain.comunity.story.model.Story;
import bloom_story.domain.location.service.LocationService;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

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

        @Schema(description = "위치 정보 경도", example = "-122.4194", requiredMode = REQUIRED)
        @NotNull
        double longitude,

        @Schema(description = "위치 정보 위도", example = "37.7749", requiredMode = REQUIRED)
        @NotNull
        double latitude,

        @Schema(description = "작성자 ID", example = "1", requiredMode = REQUIRED)
        Integer userId,

        @Schema(description = "감정 ID", example = "1", requiredMode = REQUIRED)
        Integer emotionId,

        @Schema(description = "Bloom ID", example = "1", requiredMode = REQUIRED)
        Integer bloomId
    ) {

        private static InnerStoryResponse from(Story story) {
            List<Double> points = LocationService.extractFromPoint(story.getLocation());
            return new InnerStoryResponse(
                story.getId(),
                story.getTitle(),
                points.get(0),
                points.get(1),
                story.getUser().getId(),
                story.getEmotion().getId(),
                story.getBloom().getId()
            );
        }

    }
}
