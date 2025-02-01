package bloom_story.domain.emotion.model;

import static bloom_story.domain.emotion.model.EmotionType.NEUTRAL;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

import bloom_story.global.domain.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
public class Emotion extends BaseEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Integer id;

    @NotNull
    @Column(name = "type", nullable = false, columnDefinition = "neutral")
    private String type;

    @Column(name = "color")
    private String color;

    @Column(name = "content")
    private String content;

    @Builder
    public Emotion(
        String type,
        String color,
        String content
    ) {
        this.type = type;
        this.color = color;
        this.content = content;
    }
}