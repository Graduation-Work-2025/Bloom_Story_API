package bloom_story.domain.location.controller;

import org.springframework.web.bind.annotation.RequestMapping;

import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "[NORMAL] Location: 위치 정보", description = "위치 기반 정보 관리")
@RequestMapping("/location")
public interface LocationApi {

    // @ApiResponses(
    //     value = {
    //         @ApiResponse(responseCode = "201"),
    //         @ApiResponse(responseCode = "400", content = @Content(schema = @Schema(hidden = true))),
    //         @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(hidden = true)))
    //     })
    // @Operation(summary = "내 위치 주변 스토리 목록 조회")
    // @GetMapping
    // ResponseEntity<StoriesResponse> getNearbyStories(
    //     @RequestParam double longitude,
    //     @RequestParam double latitude
    // );
}
