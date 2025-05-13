package bloom_story.domain.report.repository;

import java.util.Optional;

import org.springframework.data.repository.Repository;

import bloom_story.domain.report.model.EmotionRate;

public interface EmotionRateRepository extends Repository<EmotionRate, Integer> {

    EmotionRate save(EmotionRate emotionRate);

    Optional<EmotionRate> findById(Integer id);

    default EmotionRate getById(Integer id) {
        return findById(id)
            .orElseThrow(() -> new RuntimeException("id: " + id));
    }
}
