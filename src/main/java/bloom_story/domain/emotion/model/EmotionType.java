package bloom_story.domain.emotion.model;

import lombok.Getter;

@Getter
public enum EmotionType {
    HAPPY("기쁨"),
    SAD("슬픔"),
    DISGUST("혐오"),
    ANGRY("분노"),
    SURPRISED("놀람"),
    FEAR("공포"),
    NEUTRAL("중립");

    private final String description;

    EmotionType(String description) {
        this.description = description;
    }

    public static EmotionType getByName(String name) {
        for (EmotionType emotionType : EmotionType.values()) {
            if (emotionType.getDescription().equals(name)) {
                return emotionType;
            }
        }
        return null;
    }
}
