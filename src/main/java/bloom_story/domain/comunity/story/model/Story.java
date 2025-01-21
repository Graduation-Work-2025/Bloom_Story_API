package bloom_story.domain.comunity.story.model;

import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

import java.util.List;

import bloom_story.domain.bloom.model.Bloom;
import bloom_story.domain.comunity.comment.model.Comment;
import bloom_story.domain.emotion.Emotion;
import bloom_story.domain.user.model.User;
import bloom_story.global.domain.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "stories")
@Getter
@Setter
@NoArgsConstructor(access = PROTECTED)
public class Story extends BaseEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "content", nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(name = "likes", nullable = false)
    private int likes = 0;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "bloom_id", nullable = false)
    private Bloom bloom;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "emotion_id", nullable = false)
    private Emotion emotion;

    @Column(name = "location", nullable = false, columnDefinition = "POINT")
    private String location;

    @OneToMany(mappedBy = "story", cascade = ALL, orphanRemoval = true, fetch = LAZY)
    @JoinColumn(name = "comment_id", updatable = false)
    private List<Comment> comments;

    @OneToMany(mappedBy = "story", cascade = ALL, orphanRemoval = true, fetch = LAZY)
    @JoinColumn(name = "image_id", updatable = false)
    private List<StoryImage> images;
}