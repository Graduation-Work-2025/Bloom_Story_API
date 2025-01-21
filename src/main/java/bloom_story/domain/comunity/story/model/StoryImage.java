package bloom_story.domain.comunity.story.model;

import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

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
@Table(name = "story_images")
@Getter
@NoArgsConstructor(access = PROTECTED)
public class StoryImage extends BaseEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Integer id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "story_id", nullable = false)
    private Story story;

    @NotNull
    @Column(name = "image_url", nullable = false)
    private String imageUrl;

    @Builder
    public StoryImage(
        Integer id,
        Story story,
        String imageUrl
    ) {
        this.id = id;
        this.story = story;
        this.imageUrl = imageUrl;
    }
}