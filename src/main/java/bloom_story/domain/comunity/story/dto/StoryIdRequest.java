package bloom_story.domain.comunity.story.dto;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;

public record StoryIdRequest(
    @JsonProperty("story_id")
    @Schema(description = "스토리 id", example = "1", requiredMode = REQUIRED)
    Integer storyId
) {

}
