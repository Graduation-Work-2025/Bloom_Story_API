package bloom_story.domain.friendship.model;

import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

import java.util.ArrayList;
import java.util.List;

import bloom_story.domain.user.model.User;
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
@Table(name = "friendships")
@Getter
@NoArgsConstructor(access = PROTECTED)
public class Friendship {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Integer id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "requester_id", nullable = false)
    private User requester;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "sender_id", nullable = false)
    private User sender;

    @Column(name = "is_allowed", columnDefinition = "TINYINT")
    private Boolean isAllowed = false;

    @Builder
    public Friendship(
        User requester,
        User sender,
        Boolean isAllowed
    ) {
        this.requester = requester;
        this.sender = sender;
        this.isAllowed = isAllowed;
    }

    public void allowRequest() {
        this.isAllowed = true;
    }

    public List<User> getUsers() {
        List<User> users = new ArrayList<>();
        users.add(requester);
        users.add(sender);
        return users;
    }
}
