package bloom_story.domain.comunity.story.dto;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;

public record StoryLocationRequest(
    @JsonProperty("longitude")
    @Schema(description = "내 위치", example = "POINT(122.2356 34.67532)", requiredMode = REQUIRED)
    double longitude,

    @JsonProperty("latitude")
    @Schema(description = "내 위치", example = "POINT(122.2356 34.67532)", requiredMode = REQUIRED)
    double latitude
) {

}
