package bloom_story.domain.emotion.controller;

import org.springframework.web.bind.annotation.RequestMapping;

import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "[NORMAL] Emotion: 감정", description = "스토리 감정 분석 및 감정 관리")
@RequestMapping("/emotions")
public interface EmotionApi {

    // @ApiResponses(
    //     value = {
    //         @ApiResponse(responseCode = "201"),
    //         @ApiResponse(responseCode = "400", content = @Content(schema = @Schema(hidden = true))),
    //         @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(hidden = true)))
    //     })
    // @Operation(summary = "스토리 감정 분석")
    // @PostMapping
    // ResponseEntity<EmotionResponse> analyzeStoryEmotion(
    //     @RequestBody EmotionRequest request
    // );

    // @ApiResponses(
    //     value = {
    //         @ApiResponse(responseCode = "201"),
    //         @ApiResponse(responseCode = "400", content = @Content(schema = @Schema(hidden = true))),
    //         @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(hidden = true)))
    //     })
    // @Operation(summary = "감정 조회")
    // @GetMapping("/{id}")
    // ResponseEntity<EmotionResponse> getEmotion(
    //     @PathVariable Integer id
    // );
    //
    // @ApiResponses(
    //     value = {
    //         @ApiResponse(responseCode = "201"),
    //         @ApiResponse(responseCode = "400", content = @Content(schema = @Schema(hidden = true))),
    //         @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(hidden = true)))
    //     })
    // @Operation(summary = "모든 감정 조회")
    // @GetMapping
    // ResponseEntity<StoriesResponse> getEmotions(
    //     @RequestBody String location
    // );
    //
    // @ApiResponses(
    //     value = {
    //         @ApiResponse(responseCode = "201"),
    //         @ApiResponse(responseCode = "400", content = @Content(schema = @Schema(hidden = true))),
    //         @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(hidden = true)))
    //     })
    // @Operation(summary = "스토리 감정 수정")
    // @PutMapping("/{id}")
    // ResponseEntity<EmotionResponse> updateEmotion(
    //     @PathVariable Integer id,
    //     @RequestBody EmotionRequest request
    // );

    // @ApiResponses(
    //     value = {
    //         @ApiResponse(responseCode = "201"),
    //         @ApiResponse(responseCode = "400", content = @Content(schema = @Schema(hidden = true))),
    //         @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(hidden = true)))
    //     })
    // @Operation(summary = "스토리 삭제")
    // @DeleteMapping("/{id}")
    // ResponseEntity<Void> deleteEmotion(
    //     @PathVariable Integer id
    // );
}
