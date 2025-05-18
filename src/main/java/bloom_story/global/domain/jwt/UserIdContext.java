package bloom_story.global.domain.jwt;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import lombok.Getter;

@Component
@RequestScope
@Getter
public class UserIdContext {

    private Integer userId;

    public void setUserId(Integer userId) {
        this.userId = userId;
        System.out.println("set userId: " + userId);
    }
}
