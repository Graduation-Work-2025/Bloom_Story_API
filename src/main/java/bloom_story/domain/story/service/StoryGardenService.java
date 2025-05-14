package bloom_story.domain.story.service;

import java.time.Clock;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import bloom_story.domain.emotion.repository.EmotionBloomMapRepository;
import bloom_story.domain.emotion.repository.EmotionRepository;
import bloom_story.domain.location.service.LocationService;
import bloom_story.domain.story.dto.StoriesResponse;
import bloom_story.domain.story.dto.StoryGardenResponse;
import bloom_story.domain.story.model.Story;
import bloom_story.domain.story.repository.StoryRepository;
import bloom_story.domain.user.model.User;
import bloom_story.domain.user.repository.UserRepository;
import bloom_story.global.domain.chatgpt.EmotionAnalyticsClient;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class StoryGardenService {

    private final StoryRepository storyRepository;
    private final UserRepository userRepository;
    private final Clock clock;

    private static final double DISTANCE = 40.0;

    public StoryGardenResponse getMyStoryGarden(Integer myId) {
        User user = userRepository.getById(myId);
        List<Story> myStories = storyRepository.findAllByUserId(myId);
        return StoryGardenResponse.from(myStories);
    }

    // public StoriesResponse addStoryGarden(Integer id) {
    //     return null;
    // }
}
