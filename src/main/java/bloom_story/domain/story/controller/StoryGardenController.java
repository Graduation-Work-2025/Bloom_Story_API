package bloom_story.domain.story.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import bloom_story.domain.story.dto.StoryGardenResponse;
import bloom_story.domain.story.service.StoryGardenService;
import bloom_story.global.domain.jwt.UserId;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/stories/garden")
public class StoryGardenController implements StoryGardenApi {

    private final StoryGardenService storyGardenService;

    @GetMapping()
    public ResponseEntity<StoryGardenResponse> getStoryGarden(
        @UserId Integer myId
    ) {
        StoryGardenResponse response = storyGardenService.getMyStoryGarden(myId);
        return ResponseEntity.ok(response);
    }

    // @PutMapping("/{id}")
    // public ResponseEntity<Void> addStoryGarden(
    //     @PathVariable Integer id
    // ) {
    //     storyGardenService.addStoryGarden(id);
    //     return ResponseEntity.ok().build();
    // }

    // @DeleteMapping("/{id}")
    // public ResponseEntity<Void> deleteStoryGarden(
    //     @PathVariable Integer id
    // ) {
    //     storyGardenService.deleteStory(id);
    //     return ResponseEntity.noContent().build();
    // }
}

