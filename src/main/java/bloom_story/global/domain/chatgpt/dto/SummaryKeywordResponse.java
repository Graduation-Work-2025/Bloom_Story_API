package bloom_story.global.domain.chatgpt.dto;

import static com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import bloom_story.domain.report.model.SummaryKeyword;
import io.swagger.v3.oas.annotations.media.Schema;

@JsonNaming(value = SnakeCaseStrategy.class)
public record SummaryKeywordResponse(
    @Schema(description = "요일별 키워드 정보")
    Map<String, InnerKeywordResponse> summaries
) {
    public static SummaryKeywordResponse from(SummaryKeyword summaryKeyword) {
        ObjectMapper objectMapper = new ObjectMapper();
        LocalDate start = summaryKeyword.getStartDate();

        Map<String, InnerKeywordResponse> summaries = Map.of(
            "sunday", toInner(summaryKeyword.getSunday(), start.plusDays(0), objectMapper),
            "monday", toInner(summaryKeyword.getMonday(), start.plusDays(1), objectMapper),
            "tuesday", toInner(summaryKeyword.getTuesday(), start.plusDays(2), objectMapper),
            "wednesday", toInner(summaryKeyword.getWednesday(), start.plusDays(3), objectMapper),
            "thursday", toInner(summaryKeyword.getThursday(), start.plusDays(4), objectMapper),
            "friday", toInner(summaryKeyword.getFriday(), start.plusDays(5), objectMapper),
            "saturday", toInner(summaryKeyword.getSaturday(), start.plusDays(6), objectMapper)
        );

        return new SummaryKeywordResponse(summaries);
    }

    private static InnerKeywordResponse toInner(
        String jsonKeyword,
        LocalDate date,
        ObjectMapper objectMapper
    ) {
        if (jsonKeyword == null)
            return new InnerKeywordResponse(null, null);

        try {
            List<String> keywords = objectMapper.readValue(jsonKeyword, new TypeReference<>() {
            });
            String formattedDate = date.format(DateTimeFormatter.ofPattern("yy.MM.dd"));
            return new InnerKeywordResponse(formattedDate, keywords);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("키워드 JSON 역직렬화 실패", e);
        }
    }

    public record InnerKeywordResponse(
        @Schema(description = "요일", example = "25.05.13")
        String weekday,

        @Schema(description = "키워드 리스트")
        List<String> keyword
    ) {
    }
}

