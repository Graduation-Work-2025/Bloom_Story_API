package bloom_story.global.domain.chatgpt.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import bloom_story.global.domain.chatgpt.dto.RecommendRequest;
import bloom_story.global.domain.chatgpt.dto.RecommendResponse;
import bloom_story.global.domain.chatgpt.dto.SummaryKeywordRequest;
import bloom_story.global.domain.chatgpt.dto.SummaryKeywordResponse;
import bloom_story.global.domain.chatgpt.service.ChatGPTService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/chat-gpt")
public class ChatGPTController implements ChatGPTApi{

    private final ChatGPTService chatgptService;

    @PostMapping("/test")
    public ResponseEntity<String> testRecommend(@RequestParam String emotion) {
        String result = chatgptService.requestRecommendation(emotion);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @PostMapping("/recommend")
    public ResponseEntity<RecommendResponse> createRecommendActivity(@RequestBody RecommendRequest request) {
        RecommendResponse response = chatgptService.recommendActivity(request.emotion(), request.content());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/keywords")
    public ResponseEntity<SummaryKeywordResponse> createSummaryKeyword(@RequestBody SummaryKeywordRequest request) {
        SummaryKeywordResponse response = chatgptService.summaryLastWeekToKeyword(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}