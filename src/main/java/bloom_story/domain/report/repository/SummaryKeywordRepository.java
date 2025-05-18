package bloom_story.domain.report.repository;

import java.util.Optional;

import org.springframework.data.repository.Repository;

import bloom_story.domain.report.model.SummaryKeyword;
import bloom_story.global.domain.exception.custom.DataNotFoundException;

public interface SummaryKeywordRepository extends Repository<SummaryKeyword, Integer> {

    SummaryKeyword save(SummaryKeyword summaryKeyword);

    Optional<SummaryKeyword> findById(Integer id);

    default SummaryKeyword getById(Integer id) {
        return findById(id)
            .orElseThrow(() -> new RuntimeException("id: " + id));
    }

    Optional<SummaryKeyword> findTopByUserIdOrderByCreatedAtDesc(Integer userId);

    default SummaryKeyword getLatestByUserId(Integer userId) {
        return findTopByUserIdOrderByCreatedAtDesc(userId)
            .orElseThrow(() -> DataNotFoundException.withDetail("가장 최신의 감정 데이터가 없습니다."));
    }
}
