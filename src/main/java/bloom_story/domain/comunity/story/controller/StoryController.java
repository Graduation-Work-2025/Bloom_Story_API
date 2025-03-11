package bloom_story.domain.comunity.story.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import bloom_story.domain.comunity.story.dto.StoriesResponse;
import bloom_story.domain.comunity.story.dto.StoryRequest;
import bloom_story.domain.comunity.story.dto.StoryResponse;
import bloom_story.domain.comunity.story.service.StoryService;
import bloom_story.global.domain.jwt.UserId;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/stories")
public class StoryController implements StoryApi {

    private final StoryService storyService;

    @Operation(summary = "스토리 작성")
    @PostMapping
    public ResponseEntity<StoryResponse> createStory(
        @UserId Integer userId,
        @RequestBody StoryRequest request
    ) {
        StoryResponse response = storyService.createStory(userId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "특정 스토리 조회")
    @GetMapping("/{id}")
    public ResponseEntity<StoryResponse> getStory(
        @PathVariable Integer id
    ) {
        StoryResponse response = storyService.getStoryById(id);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "위치 기반 주변 스토리 조회")
    @GetMapping
    public ResponseEntity<StoriesResponse> getStories(
        @UserId Integer userId,
        @RequestParam double longitude,
        @RequestParam double latitude
    ) {
        StoriesResponse response = storyService.getNearbyStories(userId, longitude, latitude);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "공개된 내 스토리 목록 조회")
    @GetMapping("/my")
    public ResponseEntity<StoriesResponse> getMyStories(
        @UserId Integer userId
    ) {
        StoriesResponse response = storyService.getMyStories(userId);
        return ResponseEntity.ok(response);
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

    @Operation(summary = "스토리 삭제")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStory(
        @UserId Integer userId,
        @PathVariable Integer id
    ) {
        storyService.deleteStory(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "감정 정원에 스토리 추가")
    @PutMapping("/garden/{id}")
    public ResponseEntity<Void> addStoryGarden(
        @PathVariable Integer id
    ) {
        storyService.addStoryGarden(id);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "감정 정원 조회")
    @GetMapping("/garden")
    public ResponseEntity<StoriesResponse> getStoryGarden() {
        StoriesResponse response = storyService.getStoryByIsHighlight();
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "감정 정원에서 스토리 제거")
    @DeleteMapping("/garden/{id}")
    public ResponseEntity<Void> deleteStoryGarden(
        @PathVariable Integer id
    ) {
        storyService.deleteStory(id);
        return ResponseEntity.noContent().build();
    }
}

