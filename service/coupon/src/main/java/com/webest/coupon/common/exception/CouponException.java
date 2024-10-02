package com.webest.coupon.common.exception;

import com.webest.web.exception.ApplicationException;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class CouponException extends ApplicationException {

    private final HttpStatus httpStatus;
    private final String message;

    public CouponException(CouponErrorCode errorCode) {
        super(errorCode.getStatus(), errorCode.getMessage());
        httpStatus = errorCode.getStatus();
        message = errorCode.getMessage();
    }

}
