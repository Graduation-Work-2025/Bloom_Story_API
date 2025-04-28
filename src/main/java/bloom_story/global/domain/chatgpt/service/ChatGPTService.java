package bloom_story.global.domain.chatgpt.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import bloom_story.global.domain.chatgpt.dto.GptMessage;
import bloom_story.global.domain.chatgpt.dto.GptRequest;
import bloom_story.global.domain.chatgpt.dto.GptResponse;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ChatGPTService {

    @Value("${openai.api.key}")
    private String apiKey;

    @Value("${openai.api.model}")
    private String model;

    private final RestTemplate restTemplate = new RestTemplate();

    public String requestRecommendation(String emotion) {
        String url = "https://api.openai.com/v1/chat/completions";

        GptRequest request = new GptRequest();
        request.setModel(model);
        request.setMessages(List.of(
            new GptMessage("system", "너는 감정 상태에 따라 활동, 문구, 노래를 추천해주는 감성 큐레이터야."),
            new GptMessage("user", emotion + " 감정을 느끼는 사람에게 추천할 행동/활동/위로문구/노래를 각각 알려줘. 각각 1가지씩만.")
        ));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(apiKey);

        HttpEntity<GptRequest> entity = new HttpEntity<>(request, headers);

        ResponseEntity<GptResponse> response = restTemplate.exchange(
            url,
            HttpMethod.POST,
            entity,
            GptResponse.class
        );

        return response.getBody().getChoices().get(0).getMessage().getContent();
    }
}
