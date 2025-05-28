package bloom_story.domain.report.service;

import static bloom_story.domain.story.model.EmotionDetailType.ANNOYANCE;

import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoField;
import java.util.List;
import java.util.Map;
import java.util.Random;

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

    private final List<Map<String, String>> recommendActivities2 = List.of(
        Map.of(
            "category", "활동",
            "content", "잠깐 자리에서 일어나 스트레칭하기",
            "reason", "몸을 움직이면 긴장과 스트레스를 완화하는 데 도움이 되어 마음을 가라앉힐 수 있어요."
        ),
        Map.of(
            "category", "문구",
            "content", "지금 겪는 어려움을 하나의 미션으로 생각해보세요.",
            "reason", "지금의 어려움을 피하지 말고 하나의 도전 과제로 받아들여 보세요. 그 경험이 훗날 큰 자산이 될 것입니다."
        ),
        Map.of(
            "category", "문구",
            "content", "‘모든 일에는 때가 있다. 실패는 또 다른 배움이다.’",
            "reason", "이런 예상치 못한 문제도 결국 성장과 발전을 위한 중요한 경험임을 기억하면 마음이 한결 가벼워질 수 있습니다."
        ),
        Map.of(
            "category", "활동",
            "content", "긍정적인 자기암시 말하기",
            "reason", "‘나는 할 수 있다’, ‘이 또한 지나갈 것이다’ 같은 문장을 반복하면 마음이 강해지고 위로가 됩니다."
        ),
        Map.of(
            "category", "활동",
            "content", "시원한 물 한모금 마시기",
            "reason", "작은 행동이지만 심신을 리프레시 시켜주어 마음을 차분하게 하는 효과가 있습니다."
        )
    );

    public EmotionReportResponse getEmotionReport(Integer userId) {
        EmotionRate rate = emotionRateRepository.getLatestByUserId(userId);
        return EmotionReportResponse.from(rate);
    }


    public RecommendActivityResponse getRecommendActivity(Integer userId) {
        RecommendActivity recommend = recommendActivityRepository.getLatestByUserId(userId);
        return RecommendActivityResponse.from(recommend);
    }


    public SummaryKeywordResponse getSummaryKeyword(Integer userId) {
        SummaryKeyword keywords = summaryKeywordRepository.getLatestByUserId(userId);
        return SummaryKeywordResponse.from(keywords);
    }

    @Transactional
    public EmotionReportResponse renewalEmotionReport(Integer userId) {
        LocalDateTime today = LocalDateTime.now(clock);
        List<Story> stories = getLastWeekStories(userId);
        User user = userRepository.getById(userId);
        EmotionRate rate = EmotionRate.builder()
            .happy(0).sad(0).angry(0).disgust(0).fear(0).surprised(0)
            .user(user)
            .build();
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
        RecommendActivity recommend;
        if (recentlyStory.getEmotionDetailType().equals(ANNOYANCE)) {
            recommend = saveResponse2(user, recentlyStory);
        } else {
            RecommendResponse response = gptService.recommendActivity(
                recentlyStory.getEmotionType().getDescription(),
                recentlyStory.getContent()
            );
            recommend = RecommendActivity.builder()
                .user(user)
                .category(response.category())
                .content(response.content())
                .reason(response.reason())
                .storyId(recentlyStory.getId())
                .build();
            recommendActivityRepository.save(recommend);
        }
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
            .sunday(toJson(response.summaries().get("sunday"), objectMapper))
            .monday(toJson(response.summaries().get("monday"), objectMapper))
            .tuesday(toJson(response.summaries().get("tuesday"), objectMapper))
            .wednesday(toJson(response.summaries().get("wednesday"), objectMapper))
            .thursday(toJson(response.summaries().get("thursday"), objectMapper))
            .friday(toJson(response.summaries().get("friday"), objectMapper))
            .saturday(toJson(response.summaries().get("saturday"), objectMapper))
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

    public RecommendActivity saveResponse2(User user, Story story) {
        int randomIndex = new Random().nextInt(recommendActivities2.size());
        Map<String, String> response = recommendActivities2.get(randomIndex);
        RecommendActivity randomActivity = RecommendActivity.builder()
            .user(user)
            .storyId(story.getId())
            .category(response.get("category"))
            .content(response.get("content"))
            .reason(response.get("reason"))
            .build();

        recommendActivityRepository.save(randomActivity);
        return randomActivity;
    }
}
