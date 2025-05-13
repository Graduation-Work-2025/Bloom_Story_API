package bloom_story.domain.report.repository;

import java.util.Optional;

import org.springframework.data.repository.Repository;

import bloom_story.domain.report.model.RecommendActivity;

public interface RecommendActivityRepository extends Repository<RecommendActivity, Integer> {

    RecommendActivity save(RecommendActivity recommend);

    Optional<RecommendActivity> findById(Integer id);

    default RecommendActivity getById(Integer id) {
        return findById(id)
            .orElseThrow(() -> new RuntimeException("id: " + id));
    }
}
