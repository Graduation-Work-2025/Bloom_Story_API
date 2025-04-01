package bloom_story.global.domain.exception.custom;

import bloom_story.global.domain.exception.BloomStoryException;

public class WrongRequestException extends BloomStoryException {

    private static final String DEFAULT_MESSAGE = "잘못된 요청입니다.";

    protected WrongRequestException(String message, String detail) {
        super(message, detail);
    }

    public static WrongRequestException withDetail(String detail) {
        return new WrongRequestException(DEFAULT_MESSAGE, detail);
    }
}
