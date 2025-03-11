package bloom_story.domain.comunity.story.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import bloom_story.domain.comunity.story.model.Story;

public interface StoryRepository extends Repository<Story, Integer> {

    Story save(Story story);

    void delete(Story story);

    Optional<Story> findById(Integer id);

    // @Query(value = "SELECT * FROM stories WHERE expired_at >= :now AND user_id = :userId", nativeQuery = true)
    // List<Story> findAllByUserIdAndExpiredAtAfter(@Param("userId") Integer userId, @Param("now") LocalDateTime now);

    @Query("SELECT s FROM Story s WHERE s.expiredAt >= :now AND s.user.id = :userId")
    List<Story> findAllByUserIdAndExpiredAtAfter(@Param("userId") Integer userId, @Param("now") LocalDateTime now);

    default Story getById(Integer id) {
        return findById(id)
            .orElseThrow(() -> new RuntimeException("id: " + id));
    }

    default List<Story> findAllByLocation(String location) {
        return null;
    }
}
