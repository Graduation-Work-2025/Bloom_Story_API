package bloom_story.domain.story.service;

import java.time.Clock;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import bloom_story.domain.emotion.repository.EmotionBloomMapRepository;
import bloom_story.domain.emotion.repository.EmotionRepository;
import bloom_story.domain.location.service.LocationService;
import bloom_story.domain.story.dto.StoriesResponse;
import bloom_story.domain.story.repository.StoryRepository;
import bloom_story.domain.user.repository.UserRepository;
import bloom_story.global.domain.emotionAnalytics.EmotionAnalyticsClient;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class StoryGardenService {

    private final StoryRepository storyRepository;
    private final UserRepository userRepository;
    private final EmotionRepository emotionRepository;
    private final EmotionBloomMapRepository emotionBloomMapRepository;
    private final LocationService locationService;
    private final EmotionAnalyticsClient emotionAnalyticsClient;
    private final Clock clock;

    private static final double DISTANCE = 40.0;

    public StoriesResponse getStoryByIsHighlight() {
        return null;
    }

    public StoriesResponse addStoryGarden(Integer id) {
        return null;
    }
}
