package bloom_story.global.domain.chatgpt.dto;

import java.time.LocalDateTime;
import java.util.List;

import bloom_story.domain.story.model.Story;
import io.swagger.v3.oas.annotations.media.Schema;

public record SummaryKeywordRequest(
    @Schema(description = "스토리 목록")
    List<InnerStoryRequest> stories
) {

    public static SummaryKeywordRequest from(List<Story> stories) {
        List<InnerStoryRequest> requests = stories.stream()
            .map(InnerStoryRequest::from)
            .toList();
        return new SummaryKeywordRequest(requests);
    }

    public record InnerStoryRequest(
        @Schema(description = "스토리 고유 id", example = "1")
        Integer storyId,

        @Schema(description = "게시 시간", example = "기쁨")
        LocalDateTime createdAt,

        @Schema(description = "감정", example = "기쁨")
        String emotion,

        @Schema(description = "스토리 내용", example = "오늘은 힘든 하루였다.")
        String content
    ) {

        private static InnerStoryRequest from(Story story) {
            return new InnerStoryRequest(
                story.getId(),
                story.getCreatedAt(),
                story.getEmotionDetailType().getDescription(),
                story.getContent()
            );
        }
    }
}
