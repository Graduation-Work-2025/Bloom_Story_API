package bloom_story.domain.story.model;

import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

import java.time.LocalDateTime;

import org.locationtech.jts.geom.Point;

import com.fasterxml.jackson.annotation.JsonFormat;

import bloom_story.domain.bloom.model.Bloom;
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
    @JoinColumn(name = "bloom_id")
    private Bloom bloom;

    @NotNull
    @JoinColumn(name = "emotion_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private EmotionType emotionType;

    @JoinColumn(name = "emotion_detail_type")
    @Enumerated(EnumType.STRING)
    private EmotionDetailType emotionDetailType;

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

    @Column(name = "image_url")
    private String imageUrl;

    @Builder
    public Story(
        Integer id,
        User user,
        String content,
        int likes,
        Point location,
        SharingType sharingType,
        EmotionType emotionType,
        EmotionDetailType emotionDetailType,
        Bloom bloom,
        LocalDateTime expiredAt,
        String imageUrl
    ) {
        this.id = id;
        this.user = user;
        this.content = content;
        this.likes = likes;
        this.location = location;
        this.sharingType = sharingType;
        this.emotionType = emotionType;
        this.emotionDetailType = emotionDetailType;
        this.bloom = bloom;
        this.expiredAt = expiredAt;
        this.imageUrl = imageUrl;
    }

    public void setBloom(Bloom bloom) {
        this.bloom = bloom;
    }
}