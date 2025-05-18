package bloom_story.domain.report.controller;

import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import bloom_story.domain.report.service.ReportService;
import bloom_story.domain.user.model.User;
import bloom_story.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class ReportScheduler {

    private final ReportService reportService;
    private final UserRepository userRepository;

    @Scheduled(cron = "0 0 1 * * SUN")
    public void cacheCityBusByOpenApi() {
        try {
            List<Integer> userIds = userRepository.findAll().stream().map(User::getId).toList();
            for (Integer userId : userIds) {
                reportService.renewalEmotionReport(userId);
                reportService.renewalSummaryKeyword(userId);
            }
        } catch (Exception e) {
            log.warn("감정 레포트 스케줄링 과정에서 오류가 발생했습니다.");
        }
    }
}
