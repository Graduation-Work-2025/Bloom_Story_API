package bloom_story.global.domain.chatgpt.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

    @PostMapping("/test")
    public ResponseEntity<String> summaryWeekDiary(@RequestParam String emotion) {
        return null;
    }
}