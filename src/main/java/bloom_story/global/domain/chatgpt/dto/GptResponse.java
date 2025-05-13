package bloom_story.global.domain.chatgpt.dto;

import java.util.List;

import lombok.Data;

import lombok.Data;
import java.util.List;

@Data
public class GptResponse {
    private List<Choice> choices;

    @Data
    public static class Choice {
        private Message message;
    }

    @Data
    public static class Message {
        private String role;
        private String content; // 이 안에 JSON string으로 RecommendResponse가 있음
    }
}
