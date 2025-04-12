package bloom_story.global.domain.exception;

public abstract class BloomStoryException extends RuntimeException {

    protected final String detail;

    protected BloomStoryException(String message) {
        super(message);
        this.detail = null;
    }

    protected BloomStoryException(String message, String detail) {
        super(message);
        this.detail = detail;
    }

    public String getDetail() {
        return String.format("%s [ %s ]", getMessage(), detail);
    }
}
