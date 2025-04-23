package bloom_story.domain.story.model;

import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.locationtech.jts.geom.Point;

import com.fasterxml.jackson.annotation.JsonFormat;

import bloom_story.domain.bloom.model.Bloom;
import bloom_story.domain.emotion.model.Emotion;
import bloom_story.domain.user.model.User;
import bloom_story.global.domain.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "stories")
@Getter
@NoArgsConstructor(access = PROTECTED)
public class Story extends BaseEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Integer id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "content", columnDefinition = "TEXT")
    private String content;

    @NotNull
    @Column(name = "likes", nullable = false)
    private int likes = 0;

    @ManyToOne(fetch = LAZY, cascade = ALL)
    @JoinColumn(name = "bloom_id", nullable = false)
    private Bloom bloom;

    @NotNull
    @ManyToOne(fetch = LAZY, cascade = ALL)
    @JoinColumn(name = "emotion_id", nullable = false)
    private Emotion emotion;

    @NotNull
    @Column(name = "location", nullable = false, columnDefinition = "POINT")
    private Point location;

    @NotNull
    @Column(name = "sharing_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private SharingType sharingType;

    @Column(name = "is_highlight", columnDefinition = "TINYINT")
    private Boolean isHighlight = false;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "expired_at", columnDefinition = "TIMESTAMP")
    private LocalDateTime expiredAt;

    @OneToMany(mappedBy = "story", cascade = ALL, orphanRemoval = true, fetch = LAZY)
    private List<StoryImage> images = new ArrayList<>();

    @Builder
    public Story(
        Integer id,
        User user,
        String content,
        int likes,
        Point location,
        SharingType sharingType,
        Emotion emotion,
        Bloom bloom,
        LocalDateTime expiredAt
    ) {
        this.id = id;
        this.user = user;
        this.content = content;
        this.likes = likes;
        this.location = location;
        this.sharingType = sharingType;
        this.emotion = emotion;
        this.bloom = bloom;
        this.expiredAt = expiredAt;
    }

    public void setEmotion(Emotion emotion) {
        this.emotion = emotion;
    }

    public void setBloom(Bloom bloom) {
        this.bloom = bloom;
    }
}