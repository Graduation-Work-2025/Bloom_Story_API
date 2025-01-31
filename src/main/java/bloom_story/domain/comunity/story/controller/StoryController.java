package bloom_story.domain.comunity.story.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import bloom_story.domain.comunity.story.dto.StoriesResponse;
import bloom_story.domain.comunity.story.dto.StoryRequest;
import bloom_story.domain.comunity.story.dto.StoryResponse;
import bloom_story.domain.comunity.story.service.StoryService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/stories")
public class StoryController implements StoryApi{

    private final StoryService storyService;

    @Operation(summary = "스토리 작성")
    @PostMapping
    public ResponseEntity<StoryResponse> createStory(
        @RequestBody StoryRequest request
    ) {
        StoryResponse response = storyService.createStory(request);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "스토리 조회")
    @GetMapping("/{id}")
    public ResponseEntity<StoryResponse> getStory(
        @PathVariable Integer id
    ) {
        StoryResponse response = storyService.getStoryById(id);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "모든 스토리 조회")
    @GetMapping
    public ResponseEntity<StoriesResponse> getStories(
        @RequestBody String location
    ) {
        StoriesResponse response = storyService.getStoriesByLocation(location);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "스토리 수정")
    @PutMapping("/{id}")
    public ResponseEntity<StoryResponse> updateStory(
        @PathVariable Integer id,
        @RequestBody StoryRequest request
    ) {
        StoryResponse response = storyService.updateStory(id, request);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "스토리 삭제")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStory(
        @PathVariable Integer id
    ) {
        storyService.deleteStory(id);
        return ResponseEntity.noContent().build();
    }
}

