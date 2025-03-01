package bloom_story.domain.emotion.repository;

import java.util.Optional;

import org.springframework.data.repository.Repository;

import bloom_story.domain.emotion.model.Emotion;
import bloom_story.domain.emotion.model.EmotionType;

public interface EmotionRepository extends Repository<Emotion, Integer> {

    Emotion save(Emotion emotion);

    void delete(Emotion emotion);

    Optional<Emotion> findByType(EmotionType emotionType);

    default Emotion getByType(String type) {
        return findByType(EmotionType.getByName(type))
            .orElseThrow(() -> new RuntimeException("type: " + type));
    }
}
