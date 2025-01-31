package bloom_story.domain.user.model;

import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

import java.util.ArrayList;
import java.util.List;

import bloom_story.domain.comunity.story.model.Story;
import bloom_story.global.domain.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor(access = PROTECTED)
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Integer id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "nickname", nullable = false, unique = true)
    private String nickname;

    @NotNull
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @NotNull
    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "phone", nullable = false, unique = true)
    private String phone;

    @OneToMany(mappedBy = "user", cascade = ALL, orphanRemoval = true, fetch = LAZY)
    private List<Story> stories = new ArrayList<>();

    @Builder
    public User(
        Integer id,
        String name,
        String nickname,
        String password,
        String email,
        String phone
    ) {
        this.id = id;
        this.name = name;
        this.nickname = nickname;
        this.password = password;
        this.email = email;
        this.phone = phone;
    }
}