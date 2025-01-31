package bloom_story.domain.comunity.story.service;

import java.util.List;

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
import bloom_story.domain.emotion.repository.EmotionRepository;
import bloom_story.domain.user.model.User;
import bloom_story.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class StoryService {

    private final StoryRepository storyRepository;
    private final UserRepository userRepository;
    private final EmotionRepository emotionRepository;
    private final BloomRepository bloomRepository;

    public StoryResponse createStory(StoryRequest request) {
        User user = userRepository.getById(request.userId());
        Emotion emotion = emotionRepository.getById(request.emotionId());
        Bloom bloom = bloomRepository.getById(request.bloomId());

        Story story = Story.builder()
            .user(user)
            .title(request.title())
            .content(request.content())
            .location(request.location())
            .emotion(emotion)
            .bloom(bloom)
            .build();

        storyRepository.save(story);
        return StoryResponse.from(story);
    }

    public StoryResponse getStoryById(Integer id) {
        Story story = storyRepository.getById(id);
        return StoryResponse.from(story);
    }

    public StoriesResponse getStoriesByLocation(String location) {
        List<Story> stories = storyRepository.findAllByLocation(location);
        return StoriesResponse.from(stories);
    }

    public StoryResponse updateStory(Integer id, StoryRequest request) {
        Story story = storyRepository.getById(id);

        story = Story.builder()
            .id(story.getId())
            .user(story.getUser())
            .title(request.title())
            .content(request.content())
            .location(request.location())
            .likes(story.getLikes())
            .build();

        storyRepository.save(story);
        return StoryResponse.from(story);
    }

    public void deleteStory(Integer id) {
        Story story = storyRepository.getById(id);
        storyRepository.delete(story);
    }
}
