package com.webest.order.domain.exception;

import com.webest.web.exception.ApplicationException;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class OrderException extends ApplicationException {


    private final HttpStatus httpStatus;
    private final String message;

    public OrderException(ErrorCode errorCode) {
        super(errorCode.getStatus(), errorCode.getMessage());
        this.httpStatus = errorCode.getStatus();
        this.message = errorCode.getMessage();
    }

}
