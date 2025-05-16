package bloom_story.global.domain.chatgpt.dto;

import static com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.annotation.JsonNaming;

import io.swagger.v3.oas.annotations.media.Schema;

@JsonNaming(value = SnakeCaseStrategy.class)
public record SummaryKeywordResponse(
    @Schema(description = "요일별 키워드 정보")
    Map<String, InnerKeywordResponse> summaries
) {

    private record InnerKeywordResponse(
        @Schema(description = "요일", example = "월")
        String weekday,

        @Schema(description = "키워드 리스트")
        List<String> keyword
    ) {

    }
}
