package bloom_story.global.domain.chatgpt.dto;

import java.util.List;

import lombok.Data;

@Data
public class GptRequest {
    private String model;
    private List<GptMessage> messages;
}