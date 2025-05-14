package bloom_story.domain.story.model;

import lombok.Getter;

@Getter
public enum EmotionDetailType {
    HAPPY("기쁨", EmotionType.HAPPY),
    EXCITEMENT("신남", EmotionType.HAPPY),
    SATISFACTION("만족", EmotionType.HAPPY),
    THRILL("설렘", EmotionType.HAPPY),
    HAPPINESS("행복", EmotionType.HAPPY),

    SAD("슬픔", EmotionType.SAD),
    LONELINESS("외로움", EmotionType.SAD),
    DEPRESSION("우울", EmotionType.SAD),
    DISAPPOINTMENT("실망", EmotionType.SAD),
    EMPTINESS("허무", EmotionType.SAD),

    DISGUST("혐오", EmotionType.DISGUST),
    DISPLEASURE("불쾌", EmotionType.DISGUST),
    NAUSEA("역겨움", EmotionType.DISGUST),
    AVERSION("거부감", EmotionType.DISGUST),
    WEARINESS("싫증", EmotionType.DISGUST),

    ANGRY("분노", EmotionType.ANGRY),
    ANNOYANCE("짜증", EmotionType.ANGRY),
    FRUSTRATION("답답", EmotionType.ANGRY),
    RESENTMENT("억울", EmotionType.ANGRY),
    INDIGNATION("분개", EmotionType.ANGRY),

    SURPRISED("놀람", EmotionType.SURPRISED),
    EMBARRASSMENT("당황", EmotionType.SURPRISED),
    WONDER("경이로움", EmotionType.SURPRISED),
    CONFUSION("혼란", EmotionType.SURPRISED),
    FASCINATION("신기", EmotionType.SURPRISED),

    FEAR("공포", EmotionType.FEAR),
    ANXIETY("불안", EmotionType.FEAR),
    TENSION("긴장", EmotionType.FEAR),
    DREAD("두려움", EmotionType.FEAR),
    FRIGHT("겁남", EmotionType.FEAR);

    private final String description;
    private final EmotionType superType;

    EmotionDetailType(String description, EmotionType superType) {
        this.description = description;
        this.superType = superType;
    }

    public static EmotionDetailType getByName(String name) {
        for (EmotionDetailType emotionType : EmotionDetailType.values()) {
            if (emotionType.getDescription().equals(name)) {
                return emotionType;
            }
        }
        return null;
    }
}
