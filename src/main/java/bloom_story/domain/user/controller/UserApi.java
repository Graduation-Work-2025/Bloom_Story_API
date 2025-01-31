package bloom_story.domain.user.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import bloom_story.domain.user.dto.UserLoginRequest;
import bloom_story.domain.user.dto.UserLoginResponse;
import bloom_story.domain.user.dto.UserSignupRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "[NORMAL] User: 사용자", description = "사용자 정보 관리")
@RequestMapping("/users")
public interface UserApi {

    @ApiResponses(
        value = {
            @ApiResponse(responseCode = "201"),
            @ApiResponse(responseCode = "400", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(hidden = true)))
        })
    @Operation(summary = "사용자 회원가입")
    @PostMapping("/signup")
    ResponseEntity<UserLoginResponse> signUp(
        @RequestBody UserSignupRequest request
    );

    @ApiResponses(
        value = {
            @ApiResponse(responseCode = "201"),
            @ApiResponse(responseCode = "400", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(hidden = true)))
        })
    @Operation(summary = "사용자 로그인")
    @PostMapping("/login")
    ResponseEntity<UserLoginResponse> login(
        @RequestBody UserLoginRequest request
    );

    @ApiResponses(
        value = {
            @ApiResponse(responseCode = "201"),
            @ApiResponse(responseCode = "400", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(hidden = true)))
        })
    @Operation(summary = "사용자 정보 조회")
    @GetMapping("/{id}")
    ResponseEntity<Void> getUser(
        @PathVariable Integer id
    );

    // @ApiResponses(
    //     value = {
    //         @ApiResponse(responseCode = "201"),
    //         @ApiResponse(responseCode = "400", content = @Content(schema = @Schema(hidden = true))),
    //         @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(hidden = true)))
    //     })
    // @Operation(summary = "사용자 정보 수정")
    // @PutMapping("/{id}")
    // ResponseEntity<Void> updateUser(
    //     @PathVariable Integer id,
    //     @RequestBody UserUpdateRequest request
    // );

    @ApiResponses(
        value = {
            @ApiResponse(responseCode = "201"),
            @ApiResponse(responseCode = "400", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(hidden = true)))
        })
    @Operation(summary = "사용자 회원탈퇴")
    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteUser(
        @PathVariable Integer id
    );
}
