package bloom_story.domain.user.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import bloom_story.domain.user.dto.UserLoginRequest;
import bloom_story.domain.user.dto.UserLoginResponse;
import bloom_story.domain.user.dto.UserResponse;
import bloom_story.domain.user.dto.UserSignupRequest;
import bloom_story.domain.user.model.User;
import bloom_story.domain.user.repository.UserRepository;
import bloom_story.global.domain.exception.custom.DataNotFoundException;
import bloom_story.global.domain.jwt.UserTokenService;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private final UserTokenService userTokenService;
    private final UserValidationService userValidationService;

    public void signUp(UserSignupRequest request) {
        User newUser = User.builder()
            .userId(request.userId())
            .name(request.name())
            .nickname(request.nickname())
            .phone(request.phone())
            .password(passwordEncoder.encode(request.password()))
            .characterId(request.characterId())
            .build();

        userValidationService.checkSignUpData(newUser);
        userRepository.save(newUser);
    }

    public UserLoginResponse login(UserLoginRequest request) {
        User user = userValidationService.checkLoginData(request);
        String accessToken = userTokenService.createAccessToken(user);

        return UserLoginResponse.of(accessToken);
    }

    public UserResponse getUserInfo(Integer userId) {
        User user = userRepository.getById(userId);
        if (user == null) {
            throw DataNotFoundException.withDetail("userId : " + userId);
        }

        return UserResponse.from(user);
    }
}
