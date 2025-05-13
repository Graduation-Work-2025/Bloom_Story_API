package bloom_story.domain.report.service;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import bloom_story.domain.report.dto.EmotionReportResponse;
import bloom_story.domain.report.dto.RecommendActivityResponse;
import bloom_story.domain.report.model.EmotionRate;
import bloom_story.domain.report.model.RecommendActivity;
import bloom_story.domain.report.repository.EmotionRateRepository;
import bloom_story.domain.report.repository.RecommendActivityRepository;
import bloom_story.domain.story.model.Story;
import bloom_story.domain.story.repository.StoryRepository;
import bloom_story.domain.user.model.User;
import bloom_story.domain.user.repository.UserRepository;
import bloom_story.global.domain.chatgpt.dto.RecommendResponse;
import bloom_story.global.domain.chatgpt.service.ChatGPTService;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ReportService {

    private final UserRepository userRepository;
    private final ChatGPTService gptService;
    private final StoryRepository storyRepository;
    private final EmotionRateRepository emotionRateRepository;
    private final RecommendActivityRepository recommendActivityRepository;
    private final Clock clock;

    @Transactional
    public EmotionReportResponse getEmotionReport(Integer userId) {
        List<Story> stories = storyRepository
            .findAllByUserIdAndCreatedAtAfterOrderByCreatedAtDesc(userId, LocalDateTime.now(clock).minusDays(7));
        EmotionRate rate = EmotionRate.builder()
            .happy(0).sad(0).angry(0).disgust(0).neutral(0).fear(0).surprised(0).build();

        for (Story story : stories) {
            rate.increase(story.getEmotionType());
        }
        emotionRateRepository.save(rate);

        return EmotionReportResponse.from(rate);
    }

    @Transactional
    public RecommendActivityResponse getRecommendActivity(Integer userId) {
        User user = userRepository.getById(userId);
        Story recentlyStory = storyRepository.getTop1ByUserIdOrderByCreatedAtDesc(userId);
        RecommendResponse response = gptService.recommendActivity(
            recentlyStory.getEmotionType().getDescription(),
            recentlyStory.getContent()
        );
        RecommendActivity recommend = RecommendActivity.builder()
            .user(user)
            .category(response.category())
            .content(response.content())
            .reason(response.reason())
            .storyId(recentlyStory.getId())
            .build();
        recommendActivityRepository.save(recommend);
        return RecommendActivityResponse.from(recommend);
    }

    @Transactional
    public EmotionReportResponse getLastWeekKeyword(Integer userId) {
        User user = userRepository.getById(userId);
        //TODO: 사용자 조회 -> 최근 일주일치 스토리 가져오기 -> GPT 분석해서 요약 내용 받기 -> response에 담기
        return EmotionReportResponse.from(null);
    }
}
