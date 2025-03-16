package bloom_story.domain.user.repository;

import java.util.Optional;

import org.springframework.data.repository.Repository;

import bloom_story.domain.user.model.User;

public interface UserRepository extends Repository<User, Integer> {

    User save(User user);

    Optional<User> findById(Integer id);

    default User getById(Integer id) {
        return findById(id)
            .orElseThrow(() -> new RuntimeException("User not found"));
    }

    Optional<User> findByUserId(String userId);

    default User getByUserId(String userId) {
        return findByUserId(userId)
            .orElseThrow(() -> new RuntimeException("User not found"));
    }

    //void delete(User user);
}
