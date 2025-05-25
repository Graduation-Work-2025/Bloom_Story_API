package bloom_story.domain.report.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import bloom_story.domain.report.dto.EmotionReportResponse;
import bloom_story.domain.report.dto.RecommendActivityResponse;
import bloom_story.domain.report.service.ReportService;
import bloom_story.global.domain.chatgpt.dto.SummaryKeywordResponse;
import bloom_story.global.domain.jwt.UserId;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/reports")
public class ReportController implements ReportApi {

    private final ReportService reportService;

    @GetMapping("/emotions")
    public ResponseEntity<EmotionReportResponse> getEmotionReport(
        @UserId Integer userId
    ) {
        EmotionReportResponse response = reportService.getEmotionReport(userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/recommend")
    public ResponseEntity<RecommendActivityResponse> getRecommendActivity(
        @UserId Integer userId
    ) {
        RecommendActivityResponse response = reportService.getRecommendActivity(userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/keywords")
    public ResponseEntity<SummaryKeywordResponse> getSummaryKeyword(
        @UserId Integer userId
    ) {
        SummaryKeywordResponse response = reportService.getSummaryKeyword(userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/recommend/renewal")
    public ResponseEntity<RecommendActivityResponse> renewalRecommendActivity(
        @UserId Integer userId
    ) {
        RecommendActivityResponse response = reportService.renewalRecommendActivity(userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/renewal")
    public ResponseEntity<EmotionReportResponse> renewalReportFromManual(
        @UserId Integer userId
    ) {
        reportService.renewalEmotionReport(userId);
        reportService.renewalSummaryKeyword(userId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    // @GetMapping("/keywords/renewal")
    // public ResponseEntity<SummaryKeywordResponse> renewalSummaryKeyword(
    //     @UserId Integer userId
    // ) {
    //     SummaryKeywordResponse response = reportService.renewalSummaryKeyword(userId);
    //     return ResponseEntity.status(HttpStatus.CREATED).body(response);
    // }
}

