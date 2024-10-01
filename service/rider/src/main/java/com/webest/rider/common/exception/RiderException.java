package com.webest.rider.common.exception;

import com.webest.web.exception.ApplicationException;
import lombok.Getter;
import org.springframework.http.HttpStatus;


@Getter
public class RiderException extends ApplicationException {

    private final HttpStatus httpStatus;
    private final String message;

    public RiderException(RiderErrorCode errorCode) {
        super(errorCode.getStatus(), errorCode.getMessage());
        this.httpStatus = errorCode.getStatus();
        this.message = errorCode.getMessage();
    }

}
