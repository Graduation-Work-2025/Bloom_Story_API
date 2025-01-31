package bloom_story.domain.user.repository;

import java.util.Optional;

import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.repository.Repository;

import bloom_story.domain.user.model.User;

public interface UserRepository extends Repository<User, Integer> {

    User save(User user);

    Optional<User> findByEmail(String email);

    default User getByEmail(String email) {
        return findByEmail(email)
            .orElseThrow(() -> new RuntimeException("User not found"));
    }

    //void delete(User user);
}
