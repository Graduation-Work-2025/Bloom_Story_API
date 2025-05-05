package bloom_story.domain.report.model;

import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

import bloom_story.domain.user.model.User;
import bloom_story.global.domain.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "emotion_rates")
@Getter
@NoArgsConstructor(access = PROTECTED)
public class EmotionRate extends BaseEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Integer id;

    @Column(name = "happy", nullable = false)
    private Integer happy;

    @Column(name = "sad", nullable = false)
    private Integer sad;

    @Column(name = "fear", nullable = false)
    private Integer fear;

    @Column(name = "disgust", nullable = false)
    private Integer disgust;

    @Column(name = "angry", nullable = false)
    private Integer angry;

    @Column(name = "surprised", nullable = false)
    private Integer surprised;

    @Column(name = "neutral", nullable = false)
    private Integer neutral;


    @Builder
    public EmotionRate(
        Integer id,
        Integer happy,
        Integer sad,
        Integer fear,
        Integer disgust,
        Integer angry,
        Integer surprised,
        Integer neutral
    ) {
        this.id = id;
        this.happy = happy;
        this.sad = sad;
        this.fear = fear;
        this.disgust = disgust;
        this.angry = angry;
        this.surprised = surprised;
        this.neutral = neutral;
    }
}