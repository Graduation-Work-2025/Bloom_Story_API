package bloom_story.domain.emotion.model;

import lombok.Getter;

@Getter
public enum EmotionType {
    HAPPY("행복"),
    SAD("슬픔"),
    ANGRY("분노"),
    DISGUST("역겨움"),
    SURPRISED("놀람"),
    FEAR("공포"),
    NEUTRAL("중립");

    private final String description;

    EmotionType(String description) {
        this.description = description;
    }
}
