package bloom_story.domain.story.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import bloom_story.domain.story.model.Story;

public interface StoryRepository extends Repository<Story, Integer> {

    Story save(Story story);

    void delete(Story story);

    Optional<Story> findById(Integer id);

    default Story getById(Integer id) {
        return findById(id)
            .orElseThrow(() -> new RuntimeException("id: " + id));
    }

    Optional<Story> findTop1ByUserIdOrderByCreatedAtDesc(Integer userId);

    List<Story> findAllByUserId(Integer userId);

    default Story getTop1ByUserIdOrderByCreatedAtDesc(Integer userId) {
        return findTop1ByUserIdOrderByCreatedAtDesc(userId)
            .orElse(null);
    }

    List<Story> findAllByUserIdAndCreatedAtAfterOrderByCreatedAtDesc(Integer userId, LocalDateTime oneWeekAgo);

    @Query("SELECT s FROM Story s WHERE s.user.id = :userId")
    List<Story> findAllByUserId(@Param("userId") Integer userId, @Param("now") LocalDateTime now);

    @Query(value = "SELECT * FROM stories s " +
        "WHERE (ST_Distance_Sphere(location, ST_GeomFromText(:point, 4326)) <= :distance AND s.sharing_type = 'PUBLIC') OR s.user_id = :user_id",
        nativeQuery = true)
    List<Story> findStoriesByVisibilityAndDistance(
        @Param("point") String point, @Param("distance") double distance, @Param("user_id") Integer userId
    );

    @Query(value = "SELECT * FROM stories s "
        + "WHERE s.user_id = :user_id "
        + "AND s.created_at <= NOW() - INTERVAL 7 DAY "
        + "AND ST_Distance_Sphere(location, ST_GeomFromText(:point, 4326)) <= :distance AND s.sharing_type = 'PUBLIC' "
        + "ORDER BY s.created_at DESC LIMIT 1",
        nativeQuery = true)
    Optional<Story> findMyLastStoryByDistance(
        @Param("point") String point, @Param("distance") double distance, @Param("user_id") Integer userId
    );

    default Story getMyLastStoryByDistance(String point, double distance, Integer userId) {
        return findMyLastStoryByDistance(point, distance, userId)
            .orElse(null);
    }
}
