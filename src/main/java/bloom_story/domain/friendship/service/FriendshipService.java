package bloom_story.domain.friendship.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import bloom_story.domain.friendship.dto.FriendshipsResponse;
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
            .build();

        friendshipRepository.save(friendship);
    }

    public void allowFriendship(Integer senderId, Integer requesterId) {
        // User sender = userRepository.getById(senderId);
        // User requester = userRepository.getById(requesterId);
        Friendship friendship = friendshipRepository.getByRequesterIdAndSenderId(requesterId, senderId);

        friendship.allowRequest();
        friendshipRepository.save(friendship);
    }

    public FriendshipsResponse getFriendships(Integer userId) {
        User user = userRepository.getById(userId);
        List<Friendship> friendships = friendshipRepository.findAllByUserIdAndIsAllowed(userId, true);
        List<User> friends = null;
        // TODO: friendship 리스트에서 나를 제외한 상대방 ID 리스트 추출하기
        return FriendshipsResponse.from(friends);
    }

    public FriendshipsResponse getPendingFriendships(Integer userId) {
        User user = userRepository.getById(userId);
        List<Friendship> friendships = friendshipRepository.findAllByUserIdAndIsAllowed(userId, false);
        List<User> friends = null;
        // TODO: friendship 리스트에서 나를 제외한 상대방 ID 리스트 추출하기
        return FriendshipsResponse.from(friends);
    }
}
