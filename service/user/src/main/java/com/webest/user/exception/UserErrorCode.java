package com.webest.user.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum UserErrorCode {

    // 400
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 유저가 없습니다"),
    ACCESS_DENIED(HttpStatus.FORBIDDEN, "접근 권한이 없습니다."),
    INVALID_PASSWORD(HttpStatus.BAD_REQUEST, "잘못된 비밀번호 입니다."),

    // 500
    SOCKET_SESSION_UNKNOWN(HttpStatus.INTERNAL_SERVER_ERROR, "소켓 연결 시 세션 생성에 실패하였습니다");

    private HttpStatus status;
    private String message;


}
