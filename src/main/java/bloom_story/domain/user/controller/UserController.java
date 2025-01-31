package bloom_story.domain.user.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import bloom_story.domain.user.dto.UserLoginRequest;
import bloom_story.domain.user.dto.UserLoginResponse;
import bloom_story.domain.user.dto.UserSignupRequest;
import bloom_story.domain.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController implements UserApi {

    private final UserService userService;

    @Operation(summary = "사용자 회원가입")
    @PostMapping("/signup")
    public ResponseEntity<UserLoginResponse> signUp(
        @RequestBody UserSignupRequest request
    ) {
        UserLoginResponse response = userService.signUp(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "사용자 로그인")
    @PostMapping("/login")
    public ResponseEntity<UserLoginResponse> login(
        @RequestBody UserLoginRequest request
    ) {
        UserLoginResponse response = userService.login(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "사용자 정보 조회")
    @GetMapping("/{id}")
    public ResponseEntity<Void> getUser(
        @PathVariable Integer id
    ) {
        return ResponseEntity.ok().body(null);
    }

    // @Operation(summary = "사용자 정보 수정")
    // @PutMapping("/{id}")
    // public ResponseEntity<Void> updateUser(
    //     @PathVariable Integer id,
    //     @RequestBody UserUpdateRequest request
    // ) {
    //     return null;
    // }

    @Operation(summary = "사용자 회원탈퇴")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(
        @PathVariable Integer id
    ) {
        return ResponseEntity.ok().body(null);
    }
}
