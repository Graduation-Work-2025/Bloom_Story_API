package bloom_story.domain.user.controller;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import com.fasterxml.jackson.databind.ObjectMapper;

import bloom_story.domain.user.dto.UserLoginRequest;
import bloom_story.domain.user.dto.UserLoginResponse;
import bloom_story.domain.user.dto.UserRequest;
import bloom_story.domain.user.dto.UserResponse;
import bloom_story.domain.user.dto.UserSignupRequest;
import bloom_story.domain.user.service.UserService;
import bloom_story.global.domain.websocket.WebSocketHandler;
import bloom_story.global.domain.websocket.WebSocketRequest;
import bloom_story.global.domain.websocket.WebSocketResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserHandler implements WebSocketHandler {

    private static final String DOMAIN_USER = "user";
    private static final String SIGN_UP = "signup";
    private static final String LOGIN = "login";
    private static final String GET_USER = "get_user";

    private final UserService userService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public boolean is_supported(String command) {
        return command.equals(DOMAIN_USER);
    }

    @Override
    public WebSocketResponse<?> handle(WebSocketSession session, WebSocketRequest request) throws Exception {
        String command = request.getCommand();
        WebSocketResponse<?> response;
        String message;

        switch (command) {
            case SIGN_UP -> {
                response = signUp(request);
                message = "회원가입 성공";
            }
            case LOGIN -> {
                response = login(request);
                message = "로그인 성공";
            }
            case GET_USER -> {
                response = getUser(request);
                message = "사용자 정보 조회";
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

    @Operation(summary = "사용자 회원가입")
    public WebSocketResponse<Void> signUp(
        WebSocketRequest message
    ) {
        UserSignupRequest request = objectMapper.convertValue(message.getRequest(), UserSignupRequest.class);
        userService.signUp(request);
        return WebSocketResponse.of(0, message, null);
    }

    @Operation(summary = "사용자 로그인")
    public WebSocketResponse<UserLoginResponse> login(
        WebSocketRequest message
    ) {
        UserLoginRequest request = objectMapper.convertValue(message.getRequest(), UserLoginRequest.class);
        UserLoginResponse response = userService.login(request);
        return WebSocketResponse.of(0, message, response);
    }

    @Operation(summary = "사용자 정보 조회")
    public WebSocketResponse<UserResponse> getUser(
        WebSocketRequest message
    ) {
        UserRequest request = objectMapper.convertValue(message.getRequest(), UserRequest.class);
        UserResponse response = userService.getUserInfo(request);
        return WebSocketResponse.of(0, message, response);
    }
}
