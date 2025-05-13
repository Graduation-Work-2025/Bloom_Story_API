package bloom_story.global.domain.chatgpt.dto;

import static com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonNaming;

import io.swagger.v3.oas.annotations.media.Schema;

@JsonNaming(value = SnakeCaseStrategy.class)
public record WeekKeywordResponse(
    @Schema(description = "리포트 고유번호", example = "1", requiredMode = REQUIRED)
    Integer id,

    @Schema(description = "요일별 키워드 정보", requiredMode = REQUIRED)
    InnerEmotionRateResponse emotionRates
) {


    private record InnerEmotionRateResponse(
        @Schema(description = "요일", example = "월", requiredMode = REQUIRED)
        String weekday,

        @Schema(description = "키워드 리스트", requiredMode = REQUIRED)
        List<String> keywords
    ) {

    }
}
