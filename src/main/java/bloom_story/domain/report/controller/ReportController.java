package bloom_story.domain.report.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import bloom_story.domain.report.dto.EmotionReportResponse;
import bloom_story.domain.report.service.ReportService;
import bloom_story.domain.report.dto.RecommendActivityResponse;
import bloom_story.global.domain.jwt.UserId;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reports")
public class ReportController implements ReportApi {

    private final ReportService reportService;

    @PostMapping("/emotions")
    public ResponseEntity<EmotionReportResponse> getEmotionReport(
        @UserId Integer userId
    ) {
        EmotionReportResponse response = reportService.getEmotionReport(userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/recommend")
    public ResponseEntity<RecommendActivityResponse> getRecommendActivity(
        @UserId Integer userId
    ) {
        RecommendActivityResponse response = reportService.getRecommendActivity(userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/keywords")
    public ResponseEntity<EmotionReportResponse> getLastWeekKeyword(
        @UserId Integer userId
    ) {
        EmotionReportResponse response = reportService.getLastWeekKeyword(userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}

