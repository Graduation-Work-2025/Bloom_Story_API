package bloom_story.domain.friendship.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import bloom_story.domain.friendship.dto.FriendshipsResponse;
import bloom_story.domain.user.dto.UserLoginRequest;
import bloom_story.global.domain.jwt.UserId;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "[NORMAL] Friend: 친구", description = "친구 목록 관리")
@RequestMapping("/friends")
public interface FriendshipApi {

    @ApiResponses(
        value = {
            @ApiResponse(responseCode = "201"),
            @ApiResponse(responseCode = "400", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(hidden = true)))
        })
    @Operation(summary = "친구 추가 요청")
    @PostMapping("/{id}")
    ResponseEntity<Void> requestFriendship(
        @UserId Integer requesterId,
        @PathVariable("id") Integer senderId
    );

    @ApiResponses(
        value = {
            @ApiResponse(responseCode = "201"),
            @ApiResponse(responseCode = "400", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(hidden = true)))
        })
    @Operation(summary = "친구 추가 수락")
    @PutMapping("/{id}")
    ResponseEntity<Void> allowFriendship(
        @UserId Integer senderId,
        @PathVariable("id") Integer requesterId
    );

    @ApiResponses(
        value = {
            @ApiResponse(responseCode = "201"),
            @ApiResponse(responseCode = "400", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(hidden = true)))
        })
    @Operation(summary = "친구 목록 조회")
    @GetMapping
    ResponseEntity<FriendshipsResponse> getFriendships(
        @UserId Integer userId
    );

    @ApiResponses(
        value = {
            @ApiResponse(responseCode = "201"),
            @ApiResponse(responseCode = "400", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(hidden = true)))
        })
    @Operation(summary = "대기중인 친구 목록 조회")
    @GetMapping("/pending")
    ResponseEntity<FriendshipsResponse> getPendingFriendships(
        @UserId Integer userId
    );
}
