package bloom_story.global.domain.chatgpt;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class EmotionAnalyticsClient {

    private static final String API_URL = "http://localhost:5000/analyze";  // Flask 서버 주소

    public String analysisEmotion(String storyContent) {
        try {
            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("comments", List.of(storyContent));

            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestBody, headers);

            ResponseEntity<String> response = restTemplate.exchange(API_URL, HttpMethod.POST, request, String.class);
            System.out.println("Response: " + response.getBody());

            // JSON 응답을 리스트 형태로 변환
            ObjectMapper objectMapper = new ObjectMapper();
            List<Map<String, String>> resultList = new ArrayList<>();

            try {
                resultList = objectMapper.readValue(
                    response.getBody(),
                    new TypeReference<List<Map<String, String>>>() {
                    }
                );
            } catch (Exception e) {
                e.printStackTrace();
            }

            return resultList.get(0).get("emotion");
        } catch (Exception e) {
            return "기쁨";
        }
    }
}
