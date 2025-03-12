package bloom_story.domain.comunity.story.controller;

import static bloom_story.global.domain.websocket.model.DomainType.STORY;

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
import bloom_story.global.domain.websocket.model.CommandType;
import bloom_story.global.domain.websocket.handler.WebSocketHandler;
import bloom_story.global.domain.websocket.dto.WebSocketRequest;
import bloom_story.global.domain.websocket.dto.WebSocketResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class StoryHandler implements WebSocketHandler {

    private final StoryService storyService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public boolean is_supported(String command) {
        return command.equals(STORY.toString().toLowerCase());
    }

    @Override
    public WebSocketResponse<?> handle(WebSocketSession session, Integer userId, WebSocketRequest request) throws
        Exception {
        CommandType command = CommandType.from(request.getCommand());
        WebSocketResponse<?> response;
        String message;

        switch (command) {
            case CREATE_STORY -> {
                response = createStory(userId, request);
                message = "스토리 작성 성공";
            }
            case GET_STORY -> {
                response = getStory(userId, request);
                message = "스토리 조회 성공";
            }
            case GET_STORIES -> {
                response = getStories(userId, request);
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
        Integer userId,
        WebSocketRequest message
    ) {
        StoryRequest request = objectMapper.convertValue(message.getRequest(), StoryRequest.class);
        StoryResponse response = storyService.createStory(userId, request);

        return WebSocketResponse.of(0, message, response);
    }

    @Operation(summary = "특정 스토리 조회")
    public WebSocketResponse<StoryResponse> getStory(
        Integer userId,
        WebSocketRequest message
    ) {
        StoryIdRequest request = objectMapper.convertValue(message.getRequest(), StoryIdRequest.class);
        StoryResponse response = storyService.getStoryById(request.storyId());

        return WebSocketResponse.of(0, message, response);
    }

    @Operation(summary = "위치 기반 주변 스토리 조회")
    public WebSocketResponse<StoriesResponse> getStories(
        Integer userId,
        WebSocketRequest message
    ) {
        StoryLocationRequest request = objectMapper.convertValue(message.getRequest(), StoryLocationRequest.class);
        StoriesResponse response = storyService.getNearbyStories(userId, request.longitude(), request.latitude());
        return WebSocketResponse.of(0, message, response);
    }
}
