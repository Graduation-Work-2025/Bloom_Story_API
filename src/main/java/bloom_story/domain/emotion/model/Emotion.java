package bloom_story.domain.emotion.model;

import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

import bloom_story.domain.story.model.EmotionDetailType;
import bloom_story.domain.story.model.EmotionType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "emotions")
@Getter
@NoArgsConstructor(access = PROTECTED)
public class Emotion {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Integer id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private EmotionType type;

    @Enumerated(EnumType.STRING)
    @Column(name = "detail_type")
    private EmotionDetailType detailType;

    @Column(name = "color")
    private String color;

    @Column(name = "content")
    private String content;

    @Builder
    public Emotion(
        EmotionType type,
        EmotionDetailType detailType,
        String color,
        String content
    ) {
        this.type = type;
        this.detailType = detailType;
        this.color = color;
        this.content = content;
    }
}