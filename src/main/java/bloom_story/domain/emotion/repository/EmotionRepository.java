package bloom_story.domain.emotion.repository;

import java.util.Optional;

import org.springframework.data.repository.Repository;

import bloom_story.domain.emotion.model.Emotion;

public interface EmotionRepository extends Repository<Emotion, Integer> {

    Emotion save(Emotion emotion);

    void delete(Emotion emotion);

    Optional<Emotion> findById(Integer id);

    default Emotion getById(Integer id) {
        return findById(id)
            .orElseThrow(() -> new RuntimeException("id: " + id));
    }
}
