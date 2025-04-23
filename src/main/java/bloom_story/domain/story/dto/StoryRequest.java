package bloom_story.domain.story.dto;

import static com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

import com.fasterxml.jackson.databind.annotation.JsonNaming;

import bloom_story.domain.emotion.model.EmotionType;
import bloom_story.domain.story.model.SharingType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@JsonNaming(value = SnakeCaseStrategy.class)
public record StoryRequest(
    @Schema(description = "스토리 내용", example = "오늘은 좋은 일이 많았어요!")
    String content,

    @Schema(description = "위치 정보 경도", example = "-122.4194", requiredMode = REQUIRED)
    @NotNull
    double longitude,

    @Schema(description = "위치 정보 위도", example = "37.7749", requiredMode = REQUIRED)
    @NotNull
    double latitude,

    @Schema(description = "공개 범위", example = "PUBLIC", requiredMode = REQUIRED)
    @NotNull
    SharingType sharingType,

    @Schema(description = "감정 타입", example = "기쁨", requiredMode = REQUIRED)
    @NotNull
    String emotionType
) {

}
