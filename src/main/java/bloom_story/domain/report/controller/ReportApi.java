package bloom_story.domain.report.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import bloom_story.domain.report.dto.EmotionReportResponse;
import bloom_story.domain.report.dto.RecommendActivityResponse;
import bloom_story.global.domain.jwt.UserId;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "[NORMAL] Report: 감정 리포트", description = "사용자의 스토리 기반 감정 리포트 정보 관리")
@RequestMapping("/reports")
public interface ReportApi {

    @ApiResponses(
        value = {
            @ApiResponse(responseCode = "201"),
            @ApiResponse(responseCode = "400", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(hidden = true)))
        })
    @Operation(summary = "감정 통계")
    @GetMapping("/emotions")
    ResponseEntity<EmotionReportResponse> getEmotionReport(
        @UserId Integer userId
    );

    @ApiResponses(
        value = {
            @ApiResponse(responseCode = "201"),
            @ApiResponse(responseCode = "400", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(hidden = true)))
        })
    @Operation(summary = "추천 활동")
    @GetMapping("/recommend")
    ResponseEntity<RecommendActivityResponse> getRecommendActivity(
        @UserId Integer userId
    );

    @ApiResponses(
        value = {
            @ApiResponse(responseCode = "201"),
            @ApiResponse(responseCode = "400", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(hidden = true)))
        })
    @Operation(summary = "지난 일주일 키워드 요약")
    @GetMapping("/keywords")
    ResponseEntity<EmotionReportResponse> getLastWeekKeyword(
        @UserId Integer userId
    );
}
