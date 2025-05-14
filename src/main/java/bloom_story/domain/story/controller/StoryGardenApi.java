package bloom_story.domain.story.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import bloom_story.domain.story.dto.StoryGardenResponse;
import bloom_story.global.domain.jwt.UserId;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "[NORMAL] StoryGarden: 스토리 정원(피드)", description = "사용자의 스토리 정원 관리")
@RequestMapping("/stories/garden")
public interface StoryGardenApi {

    @ApiResponses(
        value = {
            @ApiResponse(responseCode = "201"),
            @ApiResponse(responseCode = "400", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(hidden = true)))
        })
    @Operation(summary = "내 감정 정원 조회")
    @GetMapping
    ResponseEntity<StoryGardenResponse> getStoryGarden(
        @UserId Integer userId
    );

    // @ApiResponses(
    //     value = {
    //         @ApiResponse(responseCode = "201"),
    //         @ApiResponse(responseCode = "400", content = @Content(schema = @Schema(hidden = true))),
    //         @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(hidden = true)))
    //     })
    // @Operation(summary = "감정 정원에 스토리 추가")
    // @PutMapping("/garden/{id}")
    // ResponseEntity<Void> addStoryGarden(
    //     @PathVariable Integer id
    // );

    // @ApiResponses(
    //     value = {
    //         @ApiResponse(responseCode = "201"),
    //         @ApiResponse(responseCode = "400", content = @Content(schema = @Schema(hidden = true))),
    //         @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(hidden = true)))
    //     })
    // @Operation(summary = "감정 정원에서 스토리 제거")
    // @DeleteMapping("/garden/{id}")
    // ResponseEntity<Void> deleteStoryGarden(
    //     @PathVariable Integer id
    // );
}
