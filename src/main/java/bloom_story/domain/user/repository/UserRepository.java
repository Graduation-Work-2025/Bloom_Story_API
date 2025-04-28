package bloom_story.domain.user.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import bloom_story.domain.user.model.User;
import bloom_story.global.domain.exception.custom.DataNotFoundException;

public interface UserRepository extends Repository<User, Integer> {

    User save(User user);

    Optional<User> findById(Integer id);

    default User getById(Integer id) {
        return findById(id)
            .orElseThrow(() -> DataNotFoundException.withDetail("userId: " + id));
    }

    Optional<User> findByUserId(String userId);

    default User getByUserId(String userId) {
        return findByUserId(userId)
            .orElseThrow(() -> DataNotFoundException.withDetail("userId: " + userId));
    }

    Boolean existsByUserId(String userId);

    Boolean existsByNickname(String nickname);

    Boolean existsByPhone(String phone);

    @Query(value = "SELECT * FROM users WHERE user_id LIKE CONCAT('%', :userId, '%')",
        nativeQuery = true)
    List<User> getByUserIds(@Param("userId") String userId);

    void delete(User user);
}
