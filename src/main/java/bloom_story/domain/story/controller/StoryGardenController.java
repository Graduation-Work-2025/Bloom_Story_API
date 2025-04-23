package bloom_story.domain.story.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import bloom_story.domain.story.dto.StoriesResponse;
import bloom_story.domain.story.service.StoryGardenService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/stories/garden")
public class StoryGardenController implements StoryGardenApi {

    private final StoryGardenService storyGardenService;

    @PutMapping("/{id}")
    public ResponseEntity<Void> addStoryGarden(
        @PathVariable Integer id
    ) {
        storyGardenService.addStoryGarden(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping()
    public ResponseEntity<StoriesResponse> getStoryGarden() {
        StoriesResponse response = storyGardenService.getStoryByIsHighlight();
        return ResponseEntity.ok(response);
    }

    // @DeleteMapping("/{id}")
    // public ResponseEntity<Void> deleteStoryGarden(
    //     @PathVariable Integer id
    // ) {
    //     storyGardenService.deleteStory(id);
    //     return ResponseEntity.noContent().build();
    // }
}

