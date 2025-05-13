package bloom_story.domain.report.dto;

import static com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

import com.fasterxml.jackson.databind.annotation.JsonNaming;

import bloom_story.domain.report.model.EmotionRate;
import io.swagger.v3.oas.annotations.media.Schema;

@JsonNaming(value = SnakeCaseStrategy.class)
public record EmotionReportResponse(
    @Schema(description = "기쁨 감정 비율", example = "10", requiredMode = REQUIRED)
    Integer happy,

    @Schema(description = "슬픔 감정 비율", example = "10", requiredMode = REQUIRED)
    Integer sad,

    @Schema(description = "공포 감정 비율", example = "10", requiredMode = REQUIRED)
    Integer fear,

    @Schema(description = "역겨움 감정 비율", example = "10", requiredMode = REQUIRED)
    Integer disgust,

    @Schema(description = "놀람 감정 비율", example = "10", requiredMode = REQUIRED)
    Integer surprised,

    @Schema(description = "화남 감정 비율", example = "10", requiredMode = REQUIRED)
    Integer angry,

    @Schema(description = "중립 감정 비율", example = "10", requiredMode = REQUIRED)
    Integer neutral
) {

    public static EmotionReportResponse from(EmotionRate emotionRate) {
        return new EmotionReportResponse(
            emotionRate.getHappy(),
            emotionRate.getSad(),
            emotionRate.getFear(),
            emotionRate.getDisgust(),
            emotionRate.getSurprised(),
            emotionRate.getAngry(),
            emotionRate.getNeutral()
        );
    }
}
