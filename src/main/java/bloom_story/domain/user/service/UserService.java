package bloom_story.domain.user.service;

import java.util.Optional;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import bloom_story.domain.user.dto.UserLoginRequest;
import bloom_story.domain.user.dto.UserLoginResponse;
import bloom_story.domain.user.dto.UserSignupRequest;
import bloom_story.domain.user.model.User;
import bloom_story.domain.user.repository.UserRepository;

@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public UserLoginResponse signUp(UserSignupRequest request) {
        if (userRepository.findByEmail(request.email()).isPresent()) {
            throw new RuntimeException("이미 존재하는 이메일입니다.");
        }

        User newUser = User.builder()
            .email(request.email())
            .name(request.name())
            .nickname(request.nickname())
            .phone(request.phone())
            .password(passwordEncoder.encode(request.password()))
            .build();

        userRepository.save(newUser);
        return UserLoginResponse.from(request);
    }

    public UserLoginResponse login(UserLoginRequest request) {
        User user = userRepository.getByEmail(request.email());

        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new RuntimeException("잘못된 비밀번호 입니다.");
        }

        return UserLoginResponse.fromByUser(user);
    }
}
