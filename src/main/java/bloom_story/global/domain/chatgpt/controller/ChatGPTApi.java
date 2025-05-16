package bloom_story.global.domain.chatgpt.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import bloom_story.global.domain.chatgpt.dto.RecommendRequest;
import bloom_story.global.domain.chatgpt.dto.RecommendResponse;
import bloom_story.global.domain.chatgpt.dto.SummaryKeywordRequest;
import bloom_story.global.domain.chatgpt.dto.SummaryKeywordResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "[NORMAL] ChatGPT API: 챗GPT API", description = "지피티 프롬프팅 API")
@RequestMapping("/chat-gpt")
public interface ChatGPTApi {

    @ApiResponses(
        value = {
            @ApiResponse(responseCode = "201"),
            @ApiResponse(responseCode = "400", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(hidden = true)))
        })
    @Operation(summary = "ChatGPT API (테스트용)")
    @PostMapping("/test")
    ResponseEntity<String> testRecommend(@RequestParam String emotion);

    @ApiResponses(
        value = {
            @ApiResponse(responseCode = "201"),
            @ApiResponse(responseCode = "400", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(hidden = true)))
        })
    @Operation(summary = "리포트 생성: 장소 기반 활동 추천")
    @PostMapping("/recommend")
    ResponseEntity<RecommendResponse> createRecommendActivity(
        @RequestBody RecommendRequest request
    );

    @ApiResponses(
        value = {
            @ApiResponse(responseCode = "201"),
            @ApiResponse(responseCode = "400", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(hidden = true)))
        })
    @Operation(summary = "리프토 생성: 지난 일주일 키워드 요약")
    @GetMapping("/keywords")
    ResponseEntity<SummaryKeywordResponse> createSummaryKeyword(
        @RequestBody SummaryKeywordRequest request
    );
}
