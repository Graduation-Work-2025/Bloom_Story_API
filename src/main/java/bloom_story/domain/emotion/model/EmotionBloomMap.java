package bloom_story.domain.emotion.model;

import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

import bloom_story.domain.bloom.model.Bloom;
import bloom_story.global.domain.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "emotion_bloom_map", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"emotion_id", "bloom_id"})
})
@NoArgsConstructor(access = PROTECTED)
public class EmotionBloomMap{

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "emotion_id", nullable = false)
    private Emotion emotion;

    @ManyToOne
    @JoinColumn(name = "bloom_id", nullable = false)
    private Bloom bloom;

    @Builder
    private EmotionBloomMap(Emotion emotion, Bloom bloom) {
        this.emotion = emotion;
        this.bloom = bloom;
    }
}

