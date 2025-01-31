package bloom_story.domain.emotion.controller;

import org.springframework.stereotype.Controller;

import bloom_story.domain.emotion.service.EmotionService;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class EmotionController implements EmotionApi{

    private final EmotionService emotionService;


}
