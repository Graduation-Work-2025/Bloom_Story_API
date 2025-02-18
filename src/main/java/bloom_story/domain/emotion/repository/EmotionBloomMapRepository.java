package bloom_story.domain.emotion.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.Repository;

import bloom_story.domain.bloom.model.Bloom;
import bloom_story.domain.emotion.model.Emotion;
import bloom_story.domain.emotion.model.EmotionBloomMap;

public interface EmotionBloomMapRepository extends Repository<EmotionBloomMap, Integer> {

    List<EmotionBloomMap> findAllByEmotion(Emotion emotion);
}
