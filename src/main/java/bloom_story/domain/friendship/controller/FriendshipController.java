package bloom_story.domain.friendship.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import bloom_story.domain.friendship.dto.FriendshipsResponse;
import bloom_story.domain.friendship.service.FriendshipService;
import bloom_story.global.domain.jwt.UserId;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/friends")
public class FriendshipController implements FriendshipApi {

    private final FriendshipService friendshipService;

    // TODO: 친구요청 순서? 고려하기.(양방향 친추의 경우 어떻게 되는가?)
    @Operation(summary = "친구 추가 요청")
    @PostMapping("/{friendId}")
    public ResponseEntity<Void> requestFriendship(
        @UserId Integer requesterId,
        @PathVariable("friendId") Integer senderId
    ) {
        friendshipService.requestFriendship(requesterId, senderId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "친구 추가 수락")
    @PutMapping("/{friendId}")
    public ResponseEntity<Void> allowFriendship(
        @UserId Integer senderId,
        @PathVariable("friendId") Integer requesterId
    ) {
        friendshipService.allowFriendship(senderId, requesterId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @Operation(summary = "친구 목록 조회")
    @GetMapping
    public ResponseEntity<FriendshipsResponse> getFriendships(
        @UserId Integer userId
    ) {
        FriendshipsResponse response = friendshipService.getFriendships(userId);
        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "대기중인 친구 목록 조회")
    @GetMapping("/pending")
    public ResponseEntity<FriendshipsResponse> getPendingFriendships(
        @UserId Integer userId
    ) {
        FriendshipsResponse response = friendshipService.getPendingFriendships(userId);
        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "친구 삭제")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFriendship(
        @UserId Integer userId,
        @PathVariable("id") Integer friendId
    ) {
        friendshipService.deleteFriendship(userId, friendId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
