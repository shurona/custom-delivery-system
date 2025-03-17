package com.webest.user.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum UserErrorCode {

    // 400
    // 유저 관련
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 유저가 없습니다"),
    ACCESS_DENIED(HttpStatus.FORBIDDEN, "접근 권한이 없습니다."),
    INVALID_PASSWORD(HttpStatus.BAD_REQUEST, "잘못된 비밀번호 입니다."),

    // 채팅 관련
    CHATROOM_NOT_EXIST(HttpStatus.BAD_REQUEST, "채팅방이 없습니다."),
    ILLEGAL_COMBINATION_CHATROOM(HttpStatus.BAD_REQUEST, "채팅방 생성에 필요한 올바른 역할 조합이 아닙니다"),

    // 500
    // 채팅 관련
    SOCKET_SESSION_UNKNOWN(HttpStatus.INTERNAL_SERVER_ERROR, "소켓 연결 시 세션 생성에 실패하였습니다");

    private HttpStatus status;
    private String message;


}
