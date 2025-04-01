package bloom_story.global.domain.exception.custom;

import bloom_story.global.domain.exception.BloomStoryException;

public class DataDuplicationException extends BloomStoryException {

    private static final String DEFAULT_MESSAGE = "중복되는 값이 존재합니다.";

    protected DataDuplicationException(String message, String detail) {
        super(message, detail);
    }

    public static DataDuplicationException withDetail(String detail) {
        return new DataDuplicationException(DEFAULT_MESSAGE, detail);
    }
}
