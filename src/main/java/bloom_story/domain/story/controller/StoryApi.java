package bloom_story.domain.story.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import bloom_story.domain.story.dto.CreateStoryResponse;
import bloom_story.domain.story.dto.StoriesResponse;
import bloom_story.domain.story.dto.StoryRequest;
import bloom_story.domain.story.dto.StoryResponse;
import bloom_story.global.domain.jwt.UserId;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "[NORMAL] Story: 스토리", description = "사용자의 스토리 정보 관리")
@RequestMapping("/stories")
public interface StoryApi {

    @ApiResponses(
        value = {
            @ApiResponse(responseCode = "201"),
            @ApiResponse(responseCode = "400", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(hidden = true)))
        })
    @Operation(summary = "스토리 작성")
    @PostMapping
    ResponseEntity<CreateStoryResponse> createStory(
        @UserId Integer userId,
        @RequestBody StoryRequest request
    );

    @ApiResponses(
        value = {
            @ApiResponse(responseCode = "201"),
            @ApiResponse(responseCode = "400", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(hidden = true)))
        })
    @Operation(summary = "특정 스토리 조회")
    @GetMapping("/{id}")
    ResponseEntity<StoryResponse> getStory(
        @PathVariable Integer id
    );

    @ApiResponses(
        value = {
            @ApiResponse(responseCode = "201"),
            @ApiResponse(responseCode = "400", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(hidden = true)))
        })
    @Operation(summary = "위치 기반 주변 스토리 조회")
    @GetMapping
    ResponseEntity<StoriesResponse> getStories(
        @UserId Integer userId,
        @RequestParam double longitude,
        @RequestParam double latitude
    );

    @ApiResponses(
        value = {
            @ApiResponse(responseCode = "201"),
            @ApiResponse(responseCode = "400", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(hidden = true)))
        })
    @Operation(summary = "공개된 내 스토리 목록 조회")
    @SecurityRequirement(name = "Jwt Authentication")
    @GetMapping("/my")
    ResponseEntity<StoriesResponse> getMyStories(
        @UserId Integer userId
    );

    @ApiResponses(
        value = {
            @ApiResponse(responseCode = "201"),
            @ApiResponse(responseCode = "400", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(hidden = true)))
        })
    @Operation(summary = "스토리 삭제")
    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteStory(
        @UserId Integer userId,
        @PathVariable Integer id
    );

    // @ApiResponses(
    //     value = {
    //         @ApiResponse(responseCode = "201"),
    //         @ApiResponse(responseCode = "400", content = @Content(schema = @Schema(hidden = true))),
    //         @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(hidden = true)))
    //     })
    // @Operation(summary = "스토리 수정")
    // @PutMapping("/{id}")
    // ResponseEntity<StoryResponse> updateStory(
    //     @PathVariable Integer id,
    //     @RequestBody StoryRequest request
    // );
}
