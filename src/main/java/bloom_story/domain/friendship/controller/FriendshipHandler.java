package bloom_story.domain.friendship.controller;

import static bloom_story.global.domain.websocket.model.DomainType.FRIEND;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import com.fasterxml.jackson.databind.ObjectMapper;

import bloom_story.domain.friendship.dto.FriendshipRequest;
import bloom_story.domain.friendship.dto.FriendshipsResponse;
import bloom_story.domain.friendship.dto.SearchFriendRequest;
import bloom_story.domain.friendship.dto.SearchFriendResponse;
import bloom_story.domain.friendship.service.FriendshipService;
import bloom_story.global.domain.websocket.dto.WebSocketRequest;
import bloom_story.global.domain.websocket.dto.WebSocketResponse;
import bloom_story.global.domain.websocket.handler.WebSocketHandler;
import bloom_story.global.domain.websocket.model.CommandType;
import bloom_story.global.domain.websocket.model.ErrorCode;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class FriendshipHandler implements WebSocketHandler {

    private final FriendshipService friendshipService;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final ErrorCode err = ErrorCode.builder()
        .errorCode(200)
        .build();

    @Override
    public boolean is_supported(String command) {
        return command.equals(FRIEND.toString().toLowerCase());
    }

    @Override
    public WebSocketResponse<?> handle(WebSocketSession session, Integer userId, WebSocketRequest request) throws
        Exception {
        CommandType command = CommandType.from(request.getCommand());
        WebSocketResponse<?> response;
        String message;

        switch (command) {
            case REQUEST_FRIENDSHIP -> {
                response = requestFriendship(userId, request);
                message = "친구 요청 전송";
            }
            case ALLOW_FRIENDSHIP -> {
                response = allowFriendship(userId, request);
                message = "친구 요청 수락";
            }
            case GET_FRIENDSHIPS -> {
                response = getFriendships(userId);
                message = "친구 목록 조회";
            }
            case GET_PENDING_FRIENDSHIPS -> {
                response = getPendingFriendships(userId);
                message = "친구 요청 대기 목록 조회";
            }
            case DELETE_FRIENDSHIP -> {
                response = deleteFriendship(userId, request);
                message = "친구 삭제";
            }
            case SEARCH_FRIEND -> {
                response = searchFriend(userId, request);
                message = "친구 id로 검색";
            }
            default -> {
                session.sendMessage(new TextMessage("{\"error\": \"알 수 없는 스토리 명령어\"}"));
                log.error("[WebSocket] User: 잘못된 명령어");
                return null;
            }
        }

        log.info("[WebSocket] Friend: " + message);
        return response;
    }

    @Operation(summary = "친구 요청 전송")
    public WebSocketResponse<Void> requestFriendship(
        Integer userId,
        WebSocketRequest message
    ) {
        FriendshipRequest request = objectMapper.convertValue(message.getRequest(), FriendshipRequest.class);
        friendshipService.requestFriendship(userId, request.friendId());

        return WebSocketResponse.of(err, null);
    }

    @Operation(summary = "친구 요청 수락")
    public WebSocketResponse<Void> allowFriendship(
        Integer userId,
        WebSocketRequest message
    ) {
        FriendshipRequest request = objectMapper.convertValue(message.getRequest(), FriendshipRequest.class);
        friendshipService.allowFriendship(userId, request.friendId());

        return WebSocketResponse.of(err, null);
    }

    @Operation(summary = "친구 목록 조회")
    public WebSocketResponse<SearchFriendResponse> searchFriend(
        Integer userId,
        WebSocketRequest message
    ) {
        SearchFriendRequest request = objectMapper.convertValue(message.getRequest(), SearchFriendRequest.class);
        SearchFriendResponse response = friendshipService.searchFriend(request.friendUserId());
        return WebSocketResponse.of(err, response);
    }

    @Operation(summary = "친구 목록 조회")
    public WebSocketResponse<FriendshipsResponse> getFriendships(
        Integer userId
    ) {
        FriendshipsResponse response = friendshipService.getFriendships(userId);
        return WebSocketResponse.of(err, response);
    }

    @Operation(summary = "친구 요청 대기 목록")
    public WebSocketResponse<FriendshipsResponse> getPendingFriendships(
        Integer userId
    ) {
        FriendshipsResponse response = friendshipService.getPendingFriendships(userId);
        return WebSocketResponse.of(err, response);
    }

    @Operation(summary = "친구 삭제")
    public WebSocketResponse<Void> deleteFriendship(
        Integer userId,
        WebSocketRequest message
    ) {
        FriendshipRequest request = objectMapper.convertValue(message.getRequest(), FriendshipRequest.class);
        friendshipService.deleteFriendship(userId, request.friendId());
        return WebSocketResponse.of(err, null);
    }
}
