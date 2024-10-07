package com.webest.user.exception;

import com.webest.web.exception.ApplicationException;
import lombok.Getter;
import org.springframework.http.HttpStatus;


@Getter
public class UserException extends ApplicationException {

    private final HttpStatus httpStatus;
    private final String message;

    public UserException(UserErrorCode errorCode) {
        super(errorCode.getStatus(), errorCode.getMessage());
        this.httpStatus = errorCode.getStatus();
        this.message = errorCode.getMessage();
    }

}
