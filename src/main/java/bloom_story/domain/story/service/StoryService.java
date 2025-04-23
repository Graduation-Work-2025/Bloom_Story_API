package bloom_story.domain.story.service;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

import org.locationtech.jts.geom.Point;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import bloom_story.domain.bloom.model.Bloom;
import bloom_story.domain.emotion.model.Emotion;
import bloom_story.domain.emotion.model.EmotionBloomMap;
import bloom_story.domain.emotion.model.EmotionType;
import bloom_story.domain.emotion.repository.EmotionBloomMapRepository;
import bloom_story.domain.emotion.repository.EmotionRepository;
import bloom_story.domain.location.service.LocationService;
import bloom_story.domain.story.dto.StoriesResponse;
import bloom_story.domain.story.dto.StoryRequest;
import bloom_story.domain.story.dto.StoryResponse;
import bloom_story.domain.story.model.SharingType;
import bloom_story.domain.story.model.Story;
import bloom_story.domain.story.repository.StoryRepository;
import bloom_story.domain.user.model.User;
import bloom_story.domain.user.repository.UserRepository;
import bloom_story.global.domain.emotionAnalytics.EmotionAnalyticsClient;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class StoryService {

    private final StoryRepository storyRepository;
    private final UserRepository userRepository;
    private final EmotionRepository emotionRepository;
    private final EmotionBloomMapRepository emotionBloomMapRepository;
    private final LocationService locationService;
    private final EmotionAnalyticsClient emotionAnalyticsClient;
    private final Clock clock;

    private static final double DISTANCE = 40.0;

    @Transactional
    public StoryResponse createStory(Integer userId, StoryRequest request) {
        User user = userRepository.getById(userId);
        Point point = locationService.convertToPoint(request.longitude(), request.latitude());
        EmotionType emotionType = EmotionType.getByName(request.emotionType());

        Story story = Story.builder()
            .user(user)
            .content(request.content())
            .location(point)
            .sharingType(request.sharingType())
            .emotionType(emotionType)
            .expiredAt(LocalDateTime.now(clock).plusHours(24))
            .build();

        String analyzedEmotion = emotionAnalyticsClient.analysisEmotion(story.getContent());
        Emotion emotion = emotionRepository.getByType(analyzedEmotion);
        Bloom bloom = getRandomBloom(emotion);

        story.setBloom(bloom);

        storyRepository.save(story);
        return StoryResponse.from(story);
    }

    private Bloom getRandomBloom(Emotion emotion) {
        //TODO: emotion을 gpt에게 말해서 적절한 꽃으로 표현하기(꽃말 활용?)
        List<Bloom> blooms = emotionBloomMapRepository.findAllByEmotion(emotion).stream()
            .map(EmotionBloomMap::getBloom)
            .toList();

        Random random = new Random();
        int randomNum = random.nextInt(blooms.size());
        return blooms.get(randomNum);
    }

    public StoryResponse getStoryById(Integer id) {
        Story story = storyRepository.getById(id);
        return StoryResponse.from(story);
    }

    public StoriesResponse getNearbyStories(Integer userId, double longitude, double latitude) {
        User user = userRepository.getById(userId);
        String point = String.format("POINT(%.5f %.5f)", longitude, latitude);
        List<Story> stories = storyRepository.findStoriesWithinDistance(point, DISTANCE)
            .stream()
            .filter(story -> story.getSharingType().equals(SharingType.PUBLIC) || story.getUser().equals(user))
            .toList();

        return StoriesResponse.from(stories);
    }

    public StoriesResponse getMyStories(Integer id) {
        List<Story> stories = storyRepository.findAllByUserIdAndExpiredAtAfter(id, LocalDateTime.now(clock));
        return StoriesResponse.from(stories);
    }

    // public StoryResponse updateStory(Integer id, StoryRequest request) {
    //     Story story = storyRepository.getById(id);
    //
    //
    //     story = Story.builder()
    //         .id(story.getId())
    //         .user(story.getUser())
    //         .title(request.title())
    //         .content(request.content())
    //         .location(request.location())
    //         .likes(story.getLikes())
    //         .build();
    //
    //     storyRepository.save(story);
    //     return StoryResponse.from(story);
    // }

    public void deleteStory(Integer id) {
        Story story = storyRepository.getById(id);
        storyRepository.delete(story);
    }
}
