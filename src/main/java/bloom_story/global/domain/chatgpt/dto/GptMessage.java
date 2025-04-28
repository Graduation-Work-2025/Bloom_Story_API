package bloom_story.global.domain.chatgpt.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GptMessage {
    private String role; // "system", "user", "assistant"
    private String content;
}