package bloom_story.domain.story.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import bloom_story.domain.story.dto.StoriesResponse;
import bloom_story.domain.story.dto.StoryRequest;
import bloom_story.domain.story.dto.StoryResponse;
import bloom_story.domain.story.service.StoryService;
import bloom_story.global.domain.jwt.UserId;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/stories")
public class StoryController implements StoryApi {

    private final StoryService storyService;

    @PostMapping
    public ResponseEntity<StoryResponse> createStory(
        @UserId Integer userId,
        @RequestBody StoryRequest request
    ) {
        StoryResponse response = storyService.createStory(userId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StoryResponse> getStory(
        @PathVariable Integer id
    ) {
        StoryResponse response = storyService.getStoryById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<StoriesResponse> getStories(
        @UserId Integer userId,
        @RequestParam double longitude,
        @RequestParam double latitude
    ) {
        StoriesResponse response = storyService.getNearbyStories(userId, longitude, latitude);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/my")
    public ResponseEntity<StoriesResponse> getMyStories(
        @UserId Integer userId
    ) {
        StoriesResponse response = storyService.getMyStories(userId);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "스토리 삭제")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStory(
        @UserId Integer userId,
        @PathVariable Integer id
    ) {
        storyService.deleteStory(id);
        return ResponseEntity.noContent().build();
    }

    // @Operation(summary = "스토리 수정")
    // @PutMapping("/{id}")
    // public ResponseEntity<StoryResponse> updateStory(
    //     @PathVariable Integer id,
    //     @RequestBody StoryRequest request
    // ) {
    //     StoryResponse response = storyService.updateStory(id, request);
    //     return ResponseEntity.ok(response);
    // }
}

