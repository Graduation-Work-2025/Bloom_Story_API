package bloom_story.domain.emotion.dto;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

import bloom_story.domain.emotion.model.Emotion;
import bloom_story.domain.emotion.model.EmotionType;
import io.swagger.v3.oas.annotations.media.Schema;

public record EmotionResponse(
    @Schema(description = "emotion 고유번호", example = "1", requiredMode = REQUIRED)
    Integer id,

    @Schema(description = "emotion 종류", example = "HAPPY", requiredMode = REQUIRED)
    EmotionType type,

    @Schema(description = "emotion 색상번호", example = "#4A90E2", requiredMode = REQUIRED)
    String color
) {

    public static EmotionResponse from(Emotion emotion) {
        return new EmotionResponse(
            emotion.getId(),
            emotion.getType(),
            emotion.getColor()
        );
    }
}
