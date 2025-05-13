package bloom_story.domain.report.dto;

import static com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;

import com.fasterxml.jackson.databind.annotation.JsonNaming;

import bloom_story.domain.report.model.RecommendActivity;
import io.swagger.v3.oas.annotations.media.Schema;

@JsonNaming(value = SnakeCaseStrategy.class)
public record RecommendActivityResponse(
    @Schema(description = "추천 카테고리", example = "책")
    String category,

    @Schema(description = "추천 내용", example = "베르나르 베르베르의 <시간의 무의미함>")
    String content,

    @Schema(description = "추천 이유", example = "이 책은 적막함과 공허함을 진지하게 다루어, 공감과 위로를 줄 수 있어요.")
    String reason,

    @Schema(description = "대상 스토리 ID", example = "1")
    Integer storyId
) {

    public static RecommendActivityResponse from(RecommendActivity recommendActivity) {
        return new RecommendActivityResponse(
            recommendActivity.getCategory(),
            recommendActivity.getContent(),
            recommendActivity.getReason(),
            recommendActivity.getStoryId()
        );
    }
}
