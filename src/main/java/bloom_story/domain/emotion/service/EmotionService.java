package bloom_story.domain.emotion.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import bloom_story.domain.comunity.story.model.Story;
import bloom_story.domain.emotion.dto.EmotionResponse;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class EmotionService {

    public EmotionResponse getEmotion(String emotion) {
        return null;
    }

    public EmotionResponse analyzeStoryEmotion(Story story) {
        //TODO: story의 content를 gpt api에 넣고 감정과 색상 분석받기
        return null;
    }

}
