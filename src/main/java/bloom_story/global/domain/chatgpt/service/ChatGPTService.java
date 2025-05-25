package bloom_story.global.domain.chatgpt.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import bloom_story.global.domain.chatgpt.dto.GptMessage;
import bloom_story.global.domain.chatgpt.dto.GptRequest;
import bloom_story.global.domain.chatgpt.dto.GptResponse;
import bloom_story.global.domain.chatgpt.dto.RecommendResponse;
import bloom_story.global.domain.chatgpt.dto.SummaryKeywordRequest;
import bloom_story.global.domain.chatgpt.dto.SummaryKeywordResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ChatGPTService {

    @Value("${openai.api.key}")
    private String apiKey;

    @Value("${openai.api.model}")
    private String model;

    @Value("${prompt.recommend.happy}")
    private String happyRecommendPrompt;

    @Value("${prompt.recommend.sad}")
    private String sadRecommendPrompt;

    @Value("${prompt.recommend.disgust}")
    private String disgustRecommendPrompt;

    @Value("${prompt.recommend.fear}")
    private String fearRecommendPrompt;

    @Value("${prompt.recommend.surprised}")
    private String surprisedRecommendPrompt;

    @Value("${prompt.recommend.angry}")
    private String angryRecommendPrompt;

    @Value("${prompt.summary}")
    private String summaryKeywordPrompt;

    private static final String URL = "https://api.openai.com/v1/chat/completions";

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

    public RecommendResponse recommendActivity(String emotion, String content) {
        String prompt;
        switch (emotion) {
            case "분노" -> prompt = angryRecommendPrompt;
            case "슬픔" -> prompt = sadRecommendPrompt;
            case "혐오" -> prompt = disgustRecommendPrompt;
            case "공포" -> prompt = fearRecommendPrompt;
            default -> prompt = happyRecommendPrompt;
        }

        GptRequest request = new GptRequest();
        request.setModel(model);
        request.setMessages(List.of(
            new GptMessage("system", prompt),
            new GptMessage("user", "감정: " + emotion + "\n스토리 내용: " + content)
        ));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(apiKey);

        HttpEntity<GptRequest> entity = new HttpEntity<>(request, headers);

        ResponseEntity<GptResponse> response = restTemplate.exchange(
            URL,
            HttpMethod.POST,
            entity,
            GptResponse.class
        );

        String contentJson = response.getBody()
            .getChoices()
            .get(0)
            .getMessage()
            .getContent()
            .trim();

        ObjectMapper mapper = new ObjectMapper();
        RecommendResponse result;
        try {
            result = mapper.readValue(contentJson, RecommendResponse.class);
        } catch (JsonProcessingException e) {
            log.error("GPT 응답 파싱 실패: {}", contentJson, e);
            throw new RuntimeException("GPT 응답 파싱 중 오류 발생", e);
        }
        return result;
    }

    public SummaryKeywordResponse summaryLastWeekToKeyword(SummaryKeywordRequest storyRequest) {
        GptRequest request = new GptRequest();
        request.setModel(model);
        request.setMessages(List.of(
            new GptMessage("system", summaryKeywordPrompt),
            new GptMessage("user",
                storyRequest.stories().stream()
                    .map(story ->
                        String.format("스토리_id: %d, \n작성일 %s, \n감정: %s \n내용: %s",
                            story.storyId(),
                            story.createdAt(),
                            story.emotion(),
                            story.content())
                    )
                    .collect(Collectors.joining("\n\n")))
        ));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(apiKey);

        HttpEntity<GptRequest> entity = new HttpEntity<>(request, headers);

        ResponseEntity<GptResponse> response = restTemplate.exchange(
            URL,
            HttpMethod.POST,
            entity,
            GptResponse.class
        );

        String contentJson = response.getBody()
            .getChoices()
            .get(0)
            .getMessage()
            .getContent()
            .trim();

        ObjectMapper mapper = new ObjectMapper();
        SummaryKeywordResponse result;
        try {
            result = mapper.readValue(contentJson, SummaryKeywordResponse.class);
        } catch (JsonProcessingException e) {
            log.error("GPT 응답 파싱 실패: {}", contentJson, e);
            throw new RuntimeException("GPT 응답 파싱 중 오류 발생", e);
        }
        return result;
    }
}
