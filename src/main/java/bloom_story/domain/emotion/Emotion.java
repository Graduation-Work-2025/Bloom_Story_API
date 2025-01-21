package bloom_story.domain.emotion;

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
    @Column(name = "type", nullable = false)
    private String type;

    @Column(name = "content")
    private String content;

    @Builder
    public Emotion(
        String type,
        String content
    ) {
        this.type = type;
        this.content = content;
    }
}