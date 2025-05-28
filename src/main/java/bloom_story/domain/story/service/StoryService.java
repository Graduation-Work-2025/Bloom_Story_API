package bloom_story.domain.story.service;

import static bloom_story.domain.story.model.EmotionDetailType.ANNOYANCE;
import static bloom_story.domain.story.model.EmotionDetailType.TENSION;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.PrecisionModel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import bloom_story.domain.bloom.model.Bloom;
import bloom_story.domain.bloom.repository.BloomRepository;
import bloom_story.domain.report.model.RecommendActivity;
import bloom_story.domain.report.repository.RecommendActivityRepository;
import bloom_story.domain.report.service.ReportService;
import bloom_story.domain.story.dto.CreateStoryResponse;
import bloom_story.domain.story.dto.StoriesResponse;
import bloom_story.domain.story.dto.StoryRequest;
import bloom_story.domain.story.dto.StoryResponse;
import bloom_story.domain.story.model.BloomType;
import bloom_story.domain.story.model.EmotionDetailType;
import bloom_story.domain.story.model.EmotionType;
import bloom_story.domain.story.model.Story;
import bloom_story.domain.story.repository.StoryRepository;
import bloom_story.domain.user.model.User;
import bloom_story.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class StoryService {

    private final StoryRepository storyRepository;
    private final UserRepository userRepository;
    private final BloomRepository bloomRepository;
    private final RecommendActivityRepository recommendRepository;
    private final ReportService reportService;
    private final GeometryFactory geometryFactory = new GeometryFactory(new PrecisionModel(), 4326);
    private final Clock clock;

    private final List<Map<String, String>> recommendActivities1 = List.of(
        Map.of(
            "category", "활동",
            "content", "눈을 감고 짧은 명상과 심호흡을 해보세요!",
            "reason", "긴장될 때 깊고 느린 호흡을 통해 마음을 안정시키고 집중력을 되찾을 수 있어요."
        ),
        Map.of(
            "category", "문구",
            "content", "‘실패를 두려워 말고, 경험으로 삼아라.’ – 마이클 조던",
            "reason", "실수나 긴장 상황도 성장의 과정임을 기억하면 마음이 한결 가벼워질 거에요"
        ),
        Map.of(
            "category", "활동",
            "content", "친구와 같은 주제로 이야기 나누기",
            "reason", "주변 사람과 비슷한 주제로 소소한 대화를 나누다 보면 긴장감을 완화하고 자신감을 회복할 수 있을 거에요."
        )
    );

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


    private static final double DISTANCE = 100.0;

    @Transactional
    public CreateStoryResponse createStory(Integer userId, StoryRequest request) {
        User user = userRepository.getById(userId);
        EmotionDetailType detailType = EmotionDetailType.getByName(request.emotionType());
        EmotionType emotionType = detailType.getSuperType();
        Bloom bloom = bloomRepository.getById(BloomType.getByName(emotionType).getBloomId());
        Point point = convertToPoint(request.longitude(), request.latitude());
        Integer remindStoryId = getRemindStory(userId, point);

        Story story = Story.builder()
            .user(user)
            .content(request.content())
            .location(point)
            .sharingType(request.sharingType())
            .emotionType(emotionType)
            .emotionDetailType(detailType)
            .bloom(bloom)
            .expiredAt(LocalDateTime.now(clock).plusHours(24))
            .imageUrl(request.imageUrl())
            .build();

        storyRepository.save(story);
        renewalRecommendActivity(user, story);
        return CreateStoryResponse.from(story, remindStoryId);
    }

    private Integer getRemindStory(Integer userId, Point point) {
        String pointWkt = String.format("POINT(%f %f)", point.getY(), point.getX());
        Story remindStory = storyRepository.getMyLastStoryByDistance(pointWkt, DISTANCE, userId);
        if (remindStory == null) {
            return null;
        }
        return remindStory.getId();
    }

    private Point convertToPoint(double longitude, double latitude) {
        return geometryFactory.createPoint(new Coordinate(longitude, latitude));
    }

    public StoryResponse getStoryById(Integer id) {
        Story story = storyRepository.getById(id);
        return StoryResponse.from(story);
    }

    public StoriesResponse getNearbyStories(Integer userId, double longitude, double latitude) {
        String point = String.format("POINT(%.5f %.5f)", latitude, longitude);
        List<Story> stories = storyRepository.findStoriesByVisibilityAndDistance(point, DISTANCE, userId);

        return StoriesResponse.from(userId, stories);
    }

    public StoriesResponse getMyStories(Integer myId) {
        List<Story> stories = storyRepository.findAllByUserId(myId);
        return StoriesResponse.from(myId, stories);
    }

    public void deleteStory(Integer id) {
        Story story = storyRepository.getById(id);
        storyRepository.delete(story);
    }

    // public StoryResponse updateStory(Integer id, StoryRequest request) {
    //     Story story = storyRepository.getById(id);
    //
    //
    //     story = Story.builder()
    //         .id(story.getId())
    //         .user(story.getUser())
    //         .title(request.title())
    //         .content(request.content())
    //         .location(request.location())
    //         .likes(story.getLikes())
    //         .build();
    //
    //     storyRepository.save(story);
    //     return StoryResponse.from(story);
    // }

    // private Bloom getRandomBloom(Emotion emotion) {
    //     //TODO: emotion을 gpt에게 말해서 적절한 꽃으로 표현하기(꽃말 활용?)
    //     List<Bloom> blooms = emotionBloomMapRepository.findAllByEmotion(emotion).stream()
    //         .map(EmotionBloomMap::getBloom)
    //         .toList();
    //
    //     Random random = new Random();
    //     int randomNum = random.nextInt(blooms.size());
    //     return blooms.get(randomNum);
    // }

    public void renewalRecommendActivity(User user, Story story) {
        if (story.getEmotionDetailType().equals(TENSION)) {
            saveResponse1(user, story);
        } else if (story.getEmotionDetailType().equals(ANNOYANCE)) {
            saveResponse2(user, story);
        } else {
            reportService.renewalRecommendActivityFromStory(user, story);
        }
    }

    public RecommendActivity saveResponse1(User user, Story story) {
        int randomIndex = new Random().nextInt(recommendActivities1.size());
        Map<String, String> response = recommendActivities1.get(randomIndex);
        RecommendActivity randomActivity = RecommendActivity.builder()
            .user(user)
            .storyId(story.getId())
            .category(response.get("category"))
            .content(response.get("content"))
            .reason(response.get("reason"))
            .build();

        recommendRepository.save(randomActivity);
        return randomActivity;
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

        recommendRepository.save(randomActivity);
        return randomActivity;
    }

}
