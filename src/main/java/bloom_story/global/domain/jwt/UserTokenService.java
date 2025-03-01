package bloom_story.global.domain.jwt;

import java.util.Objects;
import java.util.UUID;

import org.springframework.stereotype.Service;

import bloom_story.domain.user.model.User;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserTokenService {

    private final JwtProvider jwtProvider;
    //private final UserTokenRepository userTokenRepository;

    public String createAccessToken(User user) {
        return jwtProvider.createToken(user);
    }

    public String generateRefreshToken(User user) {
        return String.format("%s-%d", UUID.randomUUID(), user.getId());
    }

    public void checkLoginStatus(String accessToken) {
        jwtProvider.getUserId(accessToken);
    }

    // public UserToken validateRefreshToken(String refreshToken, Integer userId) {
    //     UserToken userToken = userTokenRepository.getById(userId);
    //     if (!Objects.equals(userToken.getRefreshToken(), refreshToken)) {
    //         throw new IllegalArgumentException("Invalid refresh token");
    //     }
    //     return userToken;
    // }

    public String extractUserId(String refreshToken) {
        String[] split = refreshToken.split("-");
        if (split.length == 0) {
            throw new IllegalArgumentException("Invalid refresh token");
        }
        return split[split.length - 1];
    }
}

