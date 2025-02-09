package bloom_story.domain.bloom.model;

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
@Table(name = "blooms")
@Getter
@NoArgsConstructor(access = PROTECTED)
public class Bloom extends BaseEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Integer id;

    @NotNull
    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @NotNull
    @Column(name = "type", nullable = false)
    private String type;

    @Column(name = "symbol")
    private String symbol;

    @Builder
    public Bloom(
        Integer id,
        String name,
        String type,
        String symbol
    ) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.symbol = symbol;
    }
}