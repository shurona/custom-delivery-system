package com.webest.auth.common.exception;

import com.webest.web.exception.ApplicationException;
import lombok.Getter;
import org.springframework.http.HttpStatus;


@Getter
public class AuthException extends ApplicationException {

    private final HttpStatus httpStatus;
    private final String message;

    public AuthException(AuthErrorCode errorCode) {
        super(errorCode.getStatus(), errorCode.getMessage());
        this.httpStatus = errorCode.getStatus();
        this.message = errorCode.getMessage();
    }

}
