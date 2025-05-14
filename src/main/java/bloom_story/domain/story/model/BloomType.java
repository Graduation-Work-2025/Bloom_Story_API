package bloom_story.domain.story.model;

import bloom_story.global.domain.exception.custom.DataNotFoundException;
import lombok.Getter;

@Getter
public enum BloomType {
    SUNFLOWER("해바라기", EmotionType.HAPPY, 1),
    FORGET_ME_NOT("물망초", EmotionType.SAD, 2),
    MARIGOLD("마리골드", EmotionType.DISGUST, 3),
    POPPY("양귀비", EmotionType.ANGRY, 4),
    ANEMONE("아네모네", EmotionType.SURPRISED, 5),
    BLACK_TULIP("검은튤립", EmotionType.FEAR, 6);

    private final String description;
    private final EmotionType emotionType;
    private final Integer bloomId;

    BloomType(String description, EmotionType emotionType, Integer bloomId) {
        this.description = description;
        this.emotionType = emotionType;
        this.bloomId = bloomId;
    }

    public static BloomType getByName(EmotionType emotionType) {
        for (BloomType bloomType : BloomType.values()) {
            if (bloomType.getEmotionType().equals(emotionType)) {
                return bloomType;
            }
        }
        throw DataNotFoundException.withDetail("emotion_type: " + emotionType);
    }
}
