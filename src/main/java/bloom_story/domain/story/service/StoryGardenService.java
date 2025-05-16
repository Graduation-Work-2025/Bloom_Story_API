package bloom_story.domain.story.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import bloom_story.domain.story.dto.StoryGardenResponse;
import bloom_story.domain.story.model.Story;
import bloom_story.domain.story.repository.StoryRepository;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class StoryGardenService {

    private final StoryRepository storyRepository;

    public StoryGardenResponse getMyStoryGarden(Integer myId) {
        List<Story> myStories = storyRepository.findAllByUserId(myId);
        return StoryGardenResponse.from(myStories);
    }

    // public StoriesResponse addStoryGarden(Integer id) {
    //     return null;
    // }
}
