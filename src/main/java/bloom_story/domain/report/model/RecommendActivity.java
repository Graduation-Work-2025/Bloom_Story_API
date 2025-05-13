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
@Table(name = "recommend_activities")
@Getter
@NoArgsConstructor(access = PROTECTED)
public class RecommendActivity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Integer id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "content", columnDefinition = "TEXT")
    private String content;

    @Column(name = "category", columnDefinition = "TEXT")
    private String category;

    @Column(name = "reason", columnDefinition = "TEXT")
    private String reason;

    @Column(name = "story_id", nullable = false)
    private Integer storyId;

    @Builder
    public RecommendActivity(
        Integer id,
        User user,
        String content,
        String category,
        String reason,
        Integer storyId
    ) {
        this.id = id;
        this.user = user;
        this.content = content;
        this.category = category;
        this.reason = reason;
        this.storyId = storyId;
    }
}