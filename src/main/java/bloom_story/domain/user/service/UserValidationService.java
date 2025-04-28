package bloom_story.domain.user.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import bloom_story.domain.user.dto.UserLoginRequest;
import bloom_story.domain.user.model.User;
import bloom_story.domain.user.repository.UserRepository;
import bloom_story.global.domain.exception.custom.DataDuplicationException;
import bloom_story.global.domain.exception.custom.DataNotFoundException;
import bloom_story.global.domain.exception.custom.WrongRequestException;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class UserValidationService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public void checkSignUpData(User user) {
        checkDuplicationUserId(user.getUserId());
        checkDuplicationNickName(user.getNickname());
        checkDuplicationPhone(user.getPhone());
    }

    public User checkLoginData(UserLoginRequest request) {
        User user = userRepository.getByUserId(request.userId());
        checkExistUserId(user.getUserId());
        checkCorrectPassword(request, user);
        return user;
    }

    public void checkUpdateData(User user) {
        checkDuplicationNickName(user.getNickname());
    }

    private void checkDuplicationUserId(String userId) {
        if (userRepository.existsByUserId(userId)) {
            throw DataDuplicationException.withDetail("userId: " + userId);
        }
    }

    private void checkDuplicationNickName(String nickname) {
        if (userRepository.existsByNickname(nickname)) {
            throw DataDuplicationException.withDetail("nickname: " + nickname);
        }
    }

    private void checkDuplicationPhone(String phone) {
        if (userRepository.existsByPhone(phone)) {
            throw DataDuplicationException.withDetail("phone: " + phone);
        }
    }

    private void checkExistUserId(String userId) {
        if (!userRepository.existsByUserId(userId)) {
            throw DataNotFoundException.withDetail("userId: " + userId);
        }
    }

    private void checkCorrectPassword(UserLoginRequest request, User user) {
        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw WrongRequestException.withDetail("잘못된 패스워드 입니다.");
        }
    }
}
