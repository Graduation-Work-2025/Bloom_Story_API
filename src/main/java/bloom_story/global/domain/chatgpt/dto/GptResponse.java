package bloom_story.global.domain.chatgpt.dto;

import java.util.List;

import lombok.Data;

@Data
public class GptResponse {
    private List<Choice> choices;

    @Data
    public static class Choice {
        private GptMessage message;
    }
}