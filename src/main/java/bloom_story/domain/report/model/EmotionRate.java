package bloom_story.domain.report.model;

import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

import bloom_story.domain.story.model.EmotionType;
import bloom_story.global.domain.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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

    @Column(name = "happy")
    private Integer happy = 0;

    @Column(name = "sad")
    private Integer sad = 0;

    @Column(name = "fear")
    private Integer fear = 0;

    @Column(name = "disgust")
    private Integer disgust = 0;

    @Column(name = "angry")
    private Integer angry = 0;

    @Column(name = "surprised")
    private Integer surprised = 0;

    @Builder
    public EmotionRate(
        Integer id,
        Integer happy,
        Integer sad,
        Integer fear,
        Integer disgust,
        Integer angry,
        Integer surprised
    ) {
        this.id = id;
        this.happy = happy;
        this.sad = sad;
        this.fear = fear;
        this.disgust = disgust;
        this.angry = angry;
        this.surprised = surprised;
    }

    public void increase(EmotionType emotion) {
        switch (emotion) {
            case HAPPY -> this.happy++;
            case SAD -> this.sad++;
            case FEAR -> this.fear++;
            case DISGUST -> this.disgust++;
            case ANGRY -> this.angry++;
            case SURPRISED -> this.surprised++;
        }
    }

}