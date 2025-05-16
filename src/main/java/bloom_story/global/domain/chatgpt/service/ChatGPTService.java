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
        String url = "https://api.openai.com/v1/chat/completions";

        GptRequest request = new GptRequest();
        request.setModel(model);
        request.setMessages(List.of(
            new GptMessage("system", """
                당신은 감정 기반 콘텐츠 추천 전문가입니다. 사용자가 작성한 스토리와 감정을 기반으로, 가장 적절한 활동, 책, 영화, 드라마, 혹은 짧은 문구 중 하나를 추천해 주세요.
                
                - 응답은 공감이 담긴 따뜻한 말투를 사용하세요.
                - 너무 장황한 설명은 피하고, 사용자의 감정에 집중하세요.
                - 결과는 반드시 아래 형식을 따라주세요:
                {
                  "category": "책 | 영화 | 드라마 | 활동 | 문구 중 하나",
                  "content": "추천할 콘텐츠 제목 또는 문구",
                  "reason": "짧고 따뜻한 추천 이유 (1문장)"
                }
                """),
            new GptMessage("user", "감정: " + emotion + "\n스토리 내용: " + content)
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
        String url = "https://api.openai.com/v1/chat/completions";

        GptRequest request = new GptRequest();
        request.setModel(model);
        request.setMessages(List.of(
            new GptMessage("system", """
                // 문현수님 담당, 일주일 치 스토리를 받아서 요일별 키워드들로 추출하는 프롬프팅 진행중
                위 내용을 바탕으로 요일(날짜) 별로 있었던 일들과 감정을 요약하고, 각각을 키워드로 변환해서 제공해줘.
                형식은 아래 형식으로 지켜줘.
                {
                    "summaries": {
                        "monday": {
                            "weekday": "25.05.01",
                            "keyword": ["키워드1", "키워드2", "키워드3", ...]
                        },
                        "tuesday": {
                            "weekday": "25.05.02",
                            "keyword": ["키워드1", "키워드2", "키워드3", ...]
                        },
                        "wednesday": {
                            "weekday": "25.05.03",
                            "keyword": ["키워드1", "키워드2", "키워드3", ...]
                        },
                        "thursday": {
                            "weekday": "25.05.04",
                            "keyword": ["키워드1", "키워드2", "키워드3", ...]
                        },
                        "friday": {
                            "weekday": "25.05.05",
                            "keyword": ["키워드1", "키워드2", "키워드3", ...]
                        },
                        "saturday": {
                            "weekday": "25.05.06",
                            "keyword": ["키워드1", "키워드2", "키워드3", ...]
                        },
                        "sunday": {
                            "weekday": "25.05.07",
                            "keyword": ["키워드1", "키워드2", "키워드3", ...]
                        }
                    }
                }
                """),
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
            url,
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
