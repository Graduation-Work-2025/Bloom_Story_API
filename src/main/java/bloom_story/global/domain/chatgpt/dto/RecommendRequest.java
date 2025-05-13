package bloom_story.global.domain.chatgpt.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record RecommendRequest(
    @Schema(description = "감정", example = "기쁨")
    String emotion,

    @Schema(description = "스토리 내용", example = "오늘은 힘든 하루였다.")
    String content
) {
}
