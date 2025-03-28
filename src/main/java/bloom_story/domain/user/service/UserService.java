package bloom_story.domain.user.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import bloom_story.domain.user.dto.UserLoginRequest;
import bloom_story.domain.user.dto.UserLoginResponse;
import bloom_story.domain.user.dto.UserRequest;
import bloom_story.domain.user.dto.UserResponse;
import bloom_story.domain.user.dto.UserSignupRequest;
import bloom_story.domain.user.model.User;
import bloom_story.domain.user.repository.UserRepository;
import bloom_story.global.domain.jwt.UserTokenService;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private final UserTokenService userTokenService;

    public void signUp(UserSignupRequest request) {
        if (userRepository.findByUserId(request.userId()).isPresent()) {
            throw new RuntimeException("이미 존재하는 이메일입니다.");
        }

        User newUser = User.builder()
            .userId(request.userId())
            .name(request.name())
            .nickname(request.nickname())
            .phone(request.phone())
            .password(passwordEncoder.encode(request.password()))
            .characterId(request.characterId())
            .build();

        userRepository.save(newUser);
    }

    public UserLoginResponse login(UserLoginRequest request) {
        User user = userRepository.getByUserId(request.userId());

        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new RuntimeException("잘못된 비밀번호 입니다.");
        }

        String accessToken = userTokenService.createAccessToken(user);

        return UserLoginResponse.of(accessToken);
    }

    public UserResponse getUserInfo(UserRequest request) {
        User user = userRepository.getById(request.userId());
        if (user == null) {
            throw new IllegalArgumentException("user not found.");
        }

        return UserResponse.from(user);
    }
}
