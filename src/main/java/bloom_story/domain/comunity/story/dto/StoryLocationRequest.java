package bloom_story.domain.comunity.story.dto;

import static com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

import com.fasterxml.jackson.databind.annotation.JsonNaming;

import io.swagger.v3.oas.annotations.media.Schema;

@JsonNaming(value = SnakeCaseStrategy.class)
public record StoryLocationRequest(
    @Schema(description = "내 위치", example = "POINT(122.2356 34.67532)", requiredMode = REQUIRED)
    double longitude,

    @Schema(description = "내 위치", example = "POINT(122.2356 34.67532)", requiredMode = REQUIRED)
    double latitude
) {

}
