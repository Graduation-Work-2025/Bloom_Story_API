package bloom_story.domain.story.dto;

import static com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.NOT_REQUIRED;
import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import bloom_story.domain.story.model.Story;
import bloom_story.domain.location.service.LocationService;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@JsonNaming(value = SnakeCaseStrategy.class)
public record StoriesResponse(
    List<InnerStoryResponse> stories,
    Integer count
) {

    public static StoriesResponse from(Integer userId, List<Story> stories) {
        return new StoriesResponse(
            stories.stream()
                .map(story -> InnerStoryResponse.from(userId, story))
                .toList(),
            stories.size()
        );
    }

    @JsonNaming(value = SnakeCaseStrategy.class)
    private record InnerStoryResponse(
        @Schema(description = "스토리 고유번호", example = "1", requiredMode = REQUIRED)
        Integer id,

        @Schema(description = "위치 정보 경도", example = "-122.4194", requiredMode = REQUIRED)
        @NotNull
        double longitude,

        @Schema(description = "위치 정보 위도", example = "37.7749", requiredMode = REQUIRED)
        @NotNull
        double latitude,

        @Schema(description = "작성자 ID", example = "1", requiredMode = REQUIRED)
        Integer userId,

        @Schema(description = "감정 타입", example = "기쁨", requiredMode = REQUIRED)
        String emotionType,

        @Schema(description = "Bloom ID", example = "1", requiredMode = REQUIRED)
        Integer bloomId,

        @Schema(description = "이미지 url", requiredMode = NOT_REQUIRED)
        String imageUrl,

        @Schema(description = "등록 일자", example = "2024-08-28", requiredMode = REQUIRED)
        @JsonFormat(pattern = "yyyy-MM-dd") LocalDateTime createdAt,

        @Schema(description = "내 스토리 여부", requiredMode = NOT_REQUIRED)
        Boolean isMine
    ) {

        private static InnerStoryResponse from(Integer userId, Story story) {
            List<Double> points = LocationService.extractFromPoint(story.getLocation());
            return new InnerStoryResponse(
                story.getId(),
                points.get(0),
                points.get(1),
                story.getUser().getId(),
                story.getEmotionDetailType().getDescription(),
                story.getBloom().getId(),
                story.getImageUrl() == null ? null : story.getImageUrl(),
                story.getCreatedAt(),
                userId.equals(story.getUser().getId())
            );
        }

    }
}
