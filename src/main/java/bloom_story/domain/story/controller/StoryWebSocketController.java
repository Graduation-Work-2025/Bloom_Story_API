package bloom_story.domain.story.controller;

import bloom_story.domain.story.dto.StoriesResponse;
import bloom_story.domain.story.dto.StoryLocationRequest;
import bloom_story.domain.story.service.StoryService;
import bloom_story.global.domain.websocket.dto.WebSocketResponse;
import bloom_story.global.domain.websocket.model.ErrorCode;

import lombok.RequiredArgsConstructor;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class StoryWebSocketController {

    private final StoryService storyService;
    private final SimpMessagingTemplate messagingTemplate;
    private final ErrorCode ok = ErrorCode.builder().errorCode(200).build();

    @MessageMapping("/stories")
    public void getNearbyStories(@Payload StoryLocationRequest request,
        SimpMessageHeaderAccessor headerAccessor) {

        Integer userId = (Integer) headerAccessor.getSessionAttributes().get("userId");
        StoriesResponse response = storyService.getNearbyStories(userId, request.longitude(), request.latitude());
        String sessionId = headerAccessor.getSessionId();

        messagingTemplate.convertAndSend(
            "/queue/stories-user" + sessionId,
            WebSocketResponse.of(ok, response)
        );
    }
}
