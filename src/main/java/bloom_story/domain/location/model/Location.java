package bloom_story.domain.location.model;

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
@Table(name = "locations")
@Getter
@NoArgsConstructor(access = PROTECTED)
public class Location extends BaseEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Integer id;

    @NotNull
    @Column(name = "place_name", nullable = false)
    private String placeName;

    @NotNull
    @Column(name = "location", nullable = false, columnDefinition = "POINT")
    private String location;

    @Builder
    public Location(
        Integer id,
        String placeName,
        String location
    ) {
        this.id = id;
        this.placeName = placeName;
        this.location = location;
    }
}