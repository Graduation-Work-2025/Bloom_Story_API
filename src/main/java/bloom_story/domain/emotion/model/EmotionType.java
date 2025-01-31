package bloom_story.domain.emotion.model;

public enum EmotionType {
    NEUTRAL ("중립"),
    HAPPY("행복"),
    SAD("슬픔"),
    ANGRY("분노"),
    BAD("나쁨"),
    SURPRISED("놀람"),
    FEARFUL("두려움"),
    EXCITED("흥분"),
    RELAXED("평온"),
    NERVOUS("긴장"),
    GRATEFUL("감사");

    private final String description;

    EmotionType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
