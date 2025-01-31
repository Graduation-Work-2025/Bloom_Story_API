package bloom_story.domain.comunity.story.dto;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

import bloom_story.domain.comunity.story.model.Story;
import io.swagger.v3.oas.annotations.media.Schema;

public record StoryResponse(
    @Schema(description = "스토리 고유번호", example = "1", requiredMode = REQUIRED)
    Integer id,

    @Schema(description = "스토리 제목", example = "2공에서 있었던 일", requiredMode = REQUIRED)
    String title,

    @Schema(description = "스토리 내용", example = "오늘은 좋은 일이 많았어요!")
    String content,

    @Schema(description = "위치 정보 (POINT 형식)", example = "POINT(37.7749 -122.4194)", requiredMode = REQUIRED)
    String location,

    @Schema(description = "스토리 좋아요 수", example = "0", requiredMode = REQUIRED)
    Integer likes,

    @Schema(description = "작성자 ID", example = "1", requiredMode = REQUIRED)
    Integer userId,

    @Schema(description = "감정 ID", example = "1", requiredMode = REQUIRED)
    Integer emotionId,

    @Schema(description = "Bloom ID", example = "1", requiredMode = REQUIRED)
    Integer bloomId
) {

    public static StoryResponse from(Story story) {
        return new StoryResponse(
            story.getId(),
            story.getTitle(),
            story.getContent(),
            story.getLocation(),
            story.getLikes(),
            null,
            null,
            null
            );
    }
}
