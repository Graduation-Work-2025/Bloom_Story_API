package bloom_story.domain.report.dto;

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
public record ReportResponse(
    @Schema(description = "리포트 고유번호", example = "1", requiredMode = REQUIRED)
    Integer id,

    @Schema(description = "리포트 내용", example = "이번 한 주간 있었던 일은 ~~, 느꼈던 감정은 ~~")
    String content,

    @Schema(description = "감정 비율 정보", requiredMode = REQUIRED)
    InnerEmotionRateResponse emotionRates
) {

    public static ReportResponse from(Story story) {
        return new ReportResponse(
            story.getId(),
            story.getContent(),
            null
        );
    }

    private record InnerEmotionRateResponse(
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

    }
}
