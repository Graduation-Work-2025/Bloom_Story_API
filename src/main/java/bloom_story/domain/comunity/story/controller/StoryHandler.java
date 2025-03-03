package bloom_story.domain.comunity.story.controller;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import com.fasterxml.jackson.databind.ObjectMapper;

import bloom_story.domain.comunity.story.dto.StoriesResponse;
import bloom_story.domain.comunity.story.dto.StoryIdRequest;
import bloom_story.domain.comunity.story.dto.StoryLocationRequest;
import bloom_story.domain.comunity.story.dto.StoryRequest;
import bloom_story.domain.comunity.story.dto.StoryResponse;
import bloom_story.domain.comunity.story.service.StoryService;
import bloom_story.global.domain.websocket.WebSocketHandler;
import bloom_story.global.domain.websocket.WebSocketRequest;
import bloom_story.global.domain.websocket.WebSocketResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class StoryHandler implements WebSocketHandler {

    private static final String DOMAIN_STORY = "story";
    private static final String CREATE_STORY = "create_story";
    private static final String GET_STORY = "get_story";
    private static final String GET_STORIES = "get_stories";

    private final StoryService storyService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public boolean is_supported(String command) {
        return command.equals(DOMAIN_STORY);
    }

    @Override
    public WebSocketResponse<?> handle(WebSocketSession session, WebSocketRequest request) throws Exception {
        String command = request.getCommand();
        WebSocketResponse<?> response;
        String message;

        switch (command) {
            case CREATE_STORY -> {
                response = createStory(request);
                message = "스토리 작성 성공";
            }
            case GET_STORY -> {
                response = getStory(request);
                message = "스토리 조회 성공";
            }
            case GET_STORIES -> {
                response = getStories(request);
                message = "주변 스토리 목록 조회 성공";
            }
            default -> {
                session.sendMessage(new TextMessage("{\"error\": \"알 수 없는 스토리 명령어\"}"));
                log.error("[WebSocket] User: 잘못된 명령어");
                return null;
            }
        }

        log.info("[WebSocket] User: " + message);
        session.sendMessage(new TextMessage(String.format("{\"result\": \"%s\"}", message)));
        return response;
    }

    @Operation(summary = "스토리 작성")
    public WebSocketResponse<StoryResponse> createStory(
        WebSocketRequest message
    ) {
        StoryRequest request = objectMapper.convertValue(message.getRequest(), StoryRequest.class);
        StoryResponse response = storyService.createStory(request.userId(), request);

        return WebSocketResponse.of(0, message, response);
    }

    @Operation(summary = "특정 스토리 조회")
    public WebSocketResponse<StoryResponse> getStory(
        WebSocketRequest message
    ) {
        StoryIdRequest request = objectMapper.convertValue(message.getRequest(), StoryIdRequest.class);
        StoryResponse response = storyService.getStoryById(request.storyId());

        return WebSocketResponse.of(0, message, response);
    }

    @Operation(summary = "위치 기반 주변 스토리 조회")
    public WebSocketResponse<StoriesResponse> getStories(
        WebSocketRequest message
    ) {
        StoryLocationRequest request = objectMapper.convertValue(message.getRequest(), StoryLocationRequest.class);
        StoriesResponse response = storyService.getStoriesByLocation(request.longitude(), request.latitude());
        return WebSocketResponse.of(0, message, response);
    }
}
