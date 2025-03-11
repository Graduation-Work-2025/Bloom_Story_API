package bloom_story.domain.friendship.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import bloom_story.domain.friendship.model.Friendship;
import bloom_story.domain.user.model.User;

public interface FriendshipRepository extends Repository<Friendship, Integer> {

    Friendship save(Friendship story);

    void delete(Friendship story);

    @Query(value = "SELECT * FROM friends WHERE is_allowed = :isAllowed AND (requester_id = :userId OR sender_id = :userId)",
        nativeQuery = true)
    List<Friendship> findAllByUserIdAndIsAllowed(@Param("userId") Integer userId, @Param("isAllowed") Boolean isAllowed);

    Optional<Friendship> findById(Integer id);

    default Friendship getById(Integer id) {
        return findById(id)
            .orElseThrow(() -> new RuntimeException("id: " + id));
    }

    Optional<Friendship> findByRequesterIdAndSenderId(Integer requesterId, Integer senderId);

    default Friendship getByRequesterIdAndSenderId(Integer requesterId, Integer senderId){
        return findByRequesterIdAndSenderId(requesterId, senderId)
            .orElseThrow(() -> new RuntimeException("requesterId: " + requesterId + " senderId: " + senderId));
    };
}
