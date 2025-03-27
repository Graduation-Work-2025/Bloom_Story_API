package bloom_story.global.domain.websocket.model;

import lombok.Getter;

@Getter
public enum ErrorType {
    // 회원가입
    DUPLICATE_USER_ID(400, "이미 존재하는 아이디 입니다."),
    DUPLICATE_PHONE_NUMBER(400, "해당 휴대폰 번호로 가입된 계정이 존재합니다."),
    DUPLICATE_NICKNAME(400, "이미 존재하는 닉네임 입니다."),

    // 로그인
    NO_EXIST_USER_ID(400, "존재하지 않는 아이디 입니다."),
    WRONG_PASSWORD(400, "잘못된 비밀번호 입니다.")

    // 스토리
    
    // 친구
    ;

    private final Integer code;
    private final String message;

    ErrorType(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
