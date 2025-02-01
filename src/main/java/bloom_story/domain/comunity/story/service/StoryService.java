package bloom_story.domain.comunity.story.service;

import java.util.List;

import org.locationtech.jts.geom.Point;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import bloom_story.domain.bloom.model.Bloom;
import bloom_story.domain.bloom.repository.BloomRepository;
import bloom_story.domain.comunity.story.dto.StoriesResponse;
import bloom_story.domain.comunity.story.dto.StoryRequest;
import bloom_story.domain.comunity.story.dto.StoryResponse;
import bloom_story.domain.comunity.story.model.Story;
import bloom_story.domain.comunity.story.repository.StoryRepository;
import bloom_story.domain.emotion.model.Emotion;
import bloom_story.domain.location.service.LocationService;
import bloom_story.domain.user.model.User;
import bloom_story.domain.user.repository.UserRepository;
import bloom_story.global.domain.textanalytics.TextAnalytics;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class StoryService {

    private final StoryRepository storyRepository;
    private final UserRepository userRepository;
    private final TextAnalytics textAnalytics;
    private final BloomRepository bloomRepository;

    @Transactional
    public StoryResponse createStory(StoryRequest request) {
        User user = userRepository.getById(request.userId());
        Point point = LocationService.convertToPoint(request.longitude(), request.latitude());

        Story story = Story.builder()
            .user(user)
            .title(request.title())
            .content(request.content())
            .location(point)
            .build();

        String emotion = analyzeEmotionByStory(story);
        Bloom bloom = plantBloomOfStory(emotion);

        Emotion emo = Emotion.builder()
            .type(emotion)
            .color("#FF007F")
            .content("default")
            .build();

        story.setEmotion(emo);
        story.setBloom(bloom);

        storyRepository.save(story);
        return StoryResponse.from(story);
    }

    private String analyzeEmotionByStory(Story story) {
        return textAnalytics.analyzeTextEmotion(story.getContent());
    }

    private Bloom plantBloomOfStory(String emotion) {
        //TODO: emotion을 gpt에게 말해서 적절한 꽃으로 표현하기(꽃말 활용?)

        return bloomRepository.getById(1);
    }

    public StoryResponse getStoryById(Integer id) {
        Story story = storyRepository.getById(id);
        return StoryResponse.from(story);
    }

    public StoriesResponse getStoriesByLocation(String location) {
        List<Story> stories = storyRepository.findAllByLocation(location);
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
