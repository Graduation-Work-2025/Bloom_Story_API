package bloom_story.domain.location.dto;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public record StoryRequest(
    @Schema(description = "스토리 내용", example = "오늘은 좋은 일이 많았어요!")
    String content,

    @Schema(description = "위치 정보 경도", example = "-122.4194", requiredMode = REQUIRED)
    @NotNull
    double longitude,

    @Schema(description = "위치 정보 위도", example = "37.7749", requiredMode = REQUIRED)
    @NotNull
    double latitude,

    @Schema(description = "작성자 ID", example = "1", requiredMode = REQUIRED)
    @NotNull
    Integer userId
) {

}
