package bloom_story.global.domain.exception.custom;

import bloom_story.global.domain.exception.BloomStoryException;

public class DataNotFoundException extends BloomStoryException {

    private static final String DEFAULT_MESSAGE = "데이터를 찾을 수 없습니다.";

    protected DataNotFoundException(String message, String detail) {
        super(message, detail);
    }

    public static DataNotFoundException withDetail(String detail) {
        return new DataNotFoundException(DEFAULT_MESSAGE, detail);
    }
}
