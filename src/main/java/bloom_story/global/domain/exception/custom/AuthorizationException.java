package bloom_story.global.domain.exception.custom;

import bloom_story.global.domain.exception.BloomStoryException;

public class AuthorizationException extends BloomStoryException {

    private static final String DEFAULT_MESSAGE = "권한이 없습니다.";

    protected AuthorizationException(String message, String detail) {
        super(message, detail);
    }

    public static AuthorizationException withDetail(String detail) {
        return new AuthorizationException(DEFAULT_MESSAGE, detail);
    }
}
