package com.webest.auth.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum AuthErrorCode {

    // 400
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 유저가 없습니다"),
    INVALID_PASSWORD(HttpStatus.BAD_REQUEST, "잘못된 비밀번호 입니다.");

    private HttpStatus status;
    private String message;


}
