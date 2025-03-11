package bloom_story.domain.friendship.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import bloom_story.domain.friendship.model.Friendship;

public interface FriendshipRepository extends Repository<Friendship, Integer> {

    Friendship save(Friendship story);

    void delete(Friendship story);

    @Query(value = "SELECT CASE WHEN requester_id = :userId THEN sender_id ELSE requester_id END AS friend_id "
        + "FROM friendships "
        + "WHERE (requester_id = :userId OR sender_id = :userId) AND is_allowed = :isAllowed",
        nativeQuery = true)
    List<Integer> findAllByUserIdAndIsAllowed(@Param("userId") Integer userId, @Param("isAllowed") Boolean isAllowed);

    @Query(value = "SELECT * FROM friendships "
        + "WHERE (requester_id = :userId AND sender_id = :friendId) OR (requester_id = :friendId AND sender_id = :userId)",
        nativeQuery = true)
    Optional<Friendship> findByIds(@Param("userId") Integer userId, @Param("friendId") Integer friendId);

    default Friendship getByIds(Integer userId, Integer friendId) {
        return findByIds(userId, friendId)
            .orElseThrow(() -> new RuntimeException("Could not find friendship with id " + friendId));
    }

    Optional<Friendship> findByRequesterIdAndSenderId(Integer requesterId, Integer senderId);

    default Friendship getByRequesterIdAndSenderId(Integer requesterId, Integer senderId) {
        return findByRequesterIdAndSenderId(requesterId, senderId)
            .orElseThrow(() -> new RuntimeException("requesterId: " + requesterId + " senderId: " + senderId));
    }

    ;
}
