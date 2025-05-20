package bloom_story.domain.report.service;

import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoField;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import bloom_story.domain.report.dto.EmotionReportResponse;
import bloom_story.domain.report.dto.RecommendActivityResponse;
import bloom_story.domain.report.model.EmotionRate;
import bloom_story.domain.report.model.RecommendActivity;
import bloom_story.domain.report.model.SummaryKeyword;
import bloom_story.domain.report.repository.EmotionRateRepository;
import bloom_story.domain.report.repository.RecommendActivityRepository;
import bloom_story.domain.report.repository.SummaryKeywordRepository;
import bloom_story.domain.story.model.Story;
import bloom_story.domain.story.repository.StoryRepository;
import bloom_story.domain.user.model.User;
import bloom_story.domain.user.repository.UserRepository;
import bloom_story.global.domain.chatgpt.dto.RecommendResponse;
import bloom_story.global.domain.chatgpt.dto.SummaryKeywordRequest;
import bloom_story.global.domain.chatgpt.dto.SummaryKeywordResponse;
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
    private final SummaryKeywordRepository summaryKeywordRepository;
    private final Clock clock;

    @Transactional
    public EmotionReportResponse getEmotionReport(Integer userId) {
        EmotionRate rate = emotionRateRepository.getLatestByUserId(userId);
        return EmotionReportResponse.from(rate);
    }

    @Transactional
    public RecommendActivityResponse getRecommendActivity(Integer userId) {
        RecommendActivity recommend = recommendActivityRepository.getLatestByUserId(userId);
        return RecommendActivityResponse.from(recommend);
    }

    @Transactional
    public SummaryKeywordResponse getSummaryKeyword(Integer userId) {
        SummaryKeyword keywords = summaryKeywordRepository.getLatestByUserId(userId);
        return SummaryKeywordResponse.from(keywords);
    }

    @Transactional
    public EmotionReportResponse renewalEmotionReport(Integer userId) {
        List<Story> stories = getLastWeekStories(userId);
        EmotionRate rate = EmotionRate.builder().happy(0).sad(0).angry(0).disgust(0).fear(0).surprised(0).build();
        for (Story story : stories) {
            rate.increase(story.getEmotionType());
        }
        emotionRateRepository.save(rate);

        return EmotionReportResponse.from(rate);
    }

    @Transactional
    public RecommendActivityResponse renewalRecommendActivity(Integer userId) {
        User user = userRepository.getById(userId);
        Story recentlyStory = storyRepository.getTop1ByUserIdOrderByCreatedAtDesc(userId);
        return renewalRecommendActivityFromStory(user, recentlyStory);
    }

    public RecommendActivityResponse renewalRecommendActivityFromStory(User user, Story recentlyStory) {
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
    public SummaryKeywordResponse renewalSummaryKeyword(Integer userId) {
        List<Story> stories = getLastWeekStories(userId);
        SummaryKeywordResponse response = gptService.summaryLastWeekToKeyword(SummaryKeywordRequest.from(stories));

        LocalDate startDate = LocalDate.now(clock)
            .with(ChronoField.DAY_OF_WEEK, 7)
            .minusWeeks(1);

        ObjectMapper objectMapper = new ObjectMapper();
        SummaryKeyword summaryKeyword = SummaryKeyword.builder()
            .user(userRepository.getById(userId))
            .startDate(startDate)
            .monday(toJson(response.summaries().get("monday"), objectMapper))
            .tuesday(toJson(response.summaries().get("tuesday"), objectMapper))
            .wednesday(toJson(response.summaries().get("wednesday"), objectMapper))
            .thursday(toJson(response.summaries().get("thursday"), objectMapper))
            .friday(toJson(response.summaries().get("friday"), objectMapper))
            .saturday(toJson(response.summaries().get("saturday"), objectMapper))
            .sunday(toJson(response.summaries().get("sunday"), objectMapper))
            .build();

        summaryKeywordRepository.save(summaryKeyword);
        return response;
    }

    private List<Story> getLastWeekStories(Integer userId) {
        LocalDate start = LocalDate.now(clock).with(ChronoField.DAY_OF_WEEK, 7).minusWeeks(1);
        LocalDate end = start.plusDays(6);

        return storyRepository.findAllByUserIdAndCreatedAtBetweenOrderByCreatedAtDesc(
                userId,
                start.atStartOfDay(),
                end.atTime(LocalTime.MAX)
        );
    }

    private String toJson(SummaryKeywordResponse.InnerKeywordResponse inner, ObjectMapper objectMapper) {
        if (inner == null || inner.keyword() == null) return null;
        try {
            return objectMapper.writeValueAsString(inner.keyword());
        } catch (JsonProcessingException e) {
            throw new RuntimeException("키워드 JSON 변환 실패", e);
        }
    }
}
