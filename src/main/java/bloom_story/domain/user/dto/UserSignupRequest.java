package bloom_story.domain.user.dto;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.NOT_REQUIRED;
import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;

public record UserSignupRequest(

    @JsonProperty("name")
    @Schema(description = "성명", example = "황현식", requiredMode = REQUIRED)
    String name,

    @JsonProperty("nickname")
    @Schema(description = "닉네임", example = "캔따개", requiredMode = REQUIRED)
    String nickname,

    @JsonProperty("userId")
    @Schema(description = "아이디", example = "hyunn815", requiredMode = REQUIRED)
    String userId,

    @JsonProperty("password")
    @Schema(description = "비밀번호", example = "qwer1234", requiredMode = REQUIRED)
    String password,

    @JsonProperty("phone")
    @Schema(description = "휴대폰 번호", example = "010-8434-1160", requiredMode = NOT_REQUIRED)
    String phone
) {

}
