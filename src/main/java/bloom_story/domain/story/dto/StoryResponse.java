package bloom_story.domain.story.dto;

import static com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonNaming;

import bloom_story.domain.story.model.SharingType;
import bloom_story.domain.story.model.Story;
import bloom_story.domain.location.service.LocationService;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@JsonNaming(value = SnakeCaseStrategy.class)
public record StoryResponse(
    @Schema(description = "스토리 고유번호", example = "1", requiredMode = REQUIRED)
    Integer id,

    @Schema(description = "스토리 내용", example = "오늘은 좋은 일이 많았어요!")
    String content,

    @Schema(description = "위치 정보 경도", example = "-122.4194", requiredMode = REQUIRED)
    @NotNull
    double longitude,

    @Schema(description = "위치 정보 위도", example = "37.7749", requiredMode = REQUIRED)
    @NotNull
    double latitude,

    @Schema(description = "스토리 좋아요 수", example = "0", requiredMode = REQUIRED)
    Integer likes,

    @Schema(description = "작성자 ID", example = "1", requiredMode = REQUIRED)
    Integer userId,

    @Schema(description = "감정 ID", example = "1", requiredMode = REQUIRED)
    Integer emotionId,

    @Schema(description = "Bloom ID", example = "1", requiredMode = REQUIRED)
    Integer bloomId,

    // @Schema(description = "Bloom ID", example = "1", requiredMode = REQUIRED)
    // LocalDateTime expiredAt,

    @Schema(description = "Bloom ID", example = "1", requiredMode = REQUIRED)
    SharingType sharingType
) {

    public static StoryResponse from(Story story) {
        List<Double> points = LocationService.extractFromPoint(story.getLocation());
        return new StoryResponse(
            story.getId(),
            story.getContent(),
            points.get(0),
            points.get(1),
            story.getLikes(),
            story.getUser().getId(),
            story.getEmotion().getId(),
            story.getBloom().getId(),
            story.getSharingType()
        );
    }
}
