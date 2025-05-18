package bloom_story.domain.report.repository;

import java.util.Optional;

import org.springframework.data.repository.Repository;

import bloom_story.domain.report.model.EmotionRate;
import bloom_story.domain.report.model.RecommendActivity;
import bloom_story.global.domain.exception.custom.DataNotFoundException;

public interface RecommendActivityRepository extends Repository<RecommendActivity, Integer> {

    RecommendActivity save(RecommendActivity recommend);

    Optional<RecommendActivity> findById(Integer id);

    default RecommendActivity getById(Integer id) {
        return findById(id)
            .orElseThrow(() -> new RuntimeException("id: " + id));
    }

    Optional<RecommendActivity> findTopByUserIdOrderByCreatedAtDesc(Integer userId);

    default RecommendActivity getLatestByUserId(Integer userId) {
        return findTopByUserIdOrderByCreatedAtDesc(userId)
            .orElseThrow(() -> DataNotFoundException.withDetail("가장 최신의 감정 데이터가 없습니다."));
    }
}
