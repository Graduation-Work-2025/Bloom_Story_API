package bloom_story.domain.friendship.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import bloom_story.domain.friendship.dto.FriendshipsResponse;
import bloom_story.domain.friendship.dto.SearchFriendResponse;
import bloom_story.domain.friendship.model.Friendship;
import bloom_story.domain.friendship.repository.FriendshipRepository;
import bloom_story.domain.user.model.User;
import bloom_story.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class FriendshipService {

    private final UserRepository userRepository;
    private final FriendshipRepository friendshipRepository;

    public void requestFriendship(Integer requesterId, Integer senderId) {
        User requester = userRepository.getById(requesterId);
        User sender = userRepository.getById(senderId);

        Friendship friendship = Friendship.builder()
            .requester(requester)
            .sender(sender)
            .isAllowed(false)
            .build();

        friendshipRepository.save(friendship);
    }

    public void allowFriendship(Integer senderId, Integer requesterId) {
        Friendship friendship = friendshipRepository.getByRequesterIdAndSenderId(requesterId, senderId);

        friendship.allowRequest();
        friendshipRepository.save(friendship);
    }

    public SearchFriendResponse searchFriend(String userId) {
        List<User> friends = userRepository.getByUserIds(userId);

        return SearchFriendResponse.from(friends);
    }

    public FriendshipsResponse getFriendships(Integer userId) {
        List<User> friends = getFriendsByFriendships(userId, true);

        return FriendshipsResponse.from(friends);
    }

    public FriendshipsResponse getPendingFriendships(Integer userId) {
        List<User> friends = getFriendsByFriendships(userId, false);

        return FriendshipsResponse.from(friends);
    }

    public List<User> getFriendsByFriendships(Integer userId, Boolean isAllowed) {
        List<Integer> friendships = friendshipRepository.findAllByUserIdAndIsAllowed(userId, isAllowed);
        return friendships.stream()
            .map(userRepository::getById)
            .toList();
    }

    public void deleteFriendship(Integer userId, Integer friendId) {
        Friendship friendship = friendshipRepository.getByIds(userId, friendId);
        friendshipRepository.delete(friendship);
    }
}
