package bloom_story.domain.report.model;

import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

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
@Table(name = "summary_keywords")
@Getter
@NoArgsConstructor(access = PROTECTED)
public class SummaryKeyword extends BaseEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Integer id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
    @Column(name = "start_date", columnDefinition = "TIMESTAMP")
    private LocalDate startDate;

    @Column(columnDefinition = "TEXT")
    private String sunday;

    @Column(columnDefinition = "TEXT")
    private String monday;

    @Column(columnDefinition = "TEXT")
    private String tuesday;

    @Column(columnDefinition = "TEXT")
    private String wednesday;

    @Column(columnDefinition = "TEXT")
    private String thursday;

    @Column(columnDefinition = "TEXT")
    private String friday;

    @Column(columnDefinition = "TEXT")
    private String saturday;

    @Builder
    public SummaryKeyword(
        Integer id,
        User user,
        LocalDate startDate,
        String monday,
        String tuesday,
        String wednesday,
        String thursday,
        String friday,
        String saturday,
        String sunday
    ) {
        this.id = id;
        this.user = user;
        this.startDate = startDate;
        this.monday = monday;
        this.tuesday = tuesday;
        this.wednesday = wednesday;
        this.thursday = thursday;
        this.friday = friday;
        this.saturday = saturday;
        this.sunday = sunday;
    }
}