package bloom_story.domain.report.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import bloom_story.domain.report.dto.ReportResponse;
import bloom_story.domain.report.service.ReportService;
import bloom_story.global.domain.jwt.UserId;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reports")
public class ReportController implements ReportApi {

    private final ReportService reportService;

    @PostMapping
    public ResponseEntity<ReportResponse> createReport(
        @UserId Integer userId
    ) {
        ReportResponse response = reportService.createReport(userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}

