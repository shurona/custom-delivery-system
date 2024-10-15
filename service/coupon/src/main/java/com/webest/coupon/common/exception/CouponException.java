package com.webest.coupon.common.exception;

import com.webest.web.exception.ApplicationException;
import lombok.Getter;

@Getter
public class CouponException extends ApplicationException {
    
    public CouponException(CouponErrorCode errorCode) {
        super(errorCode.getStatus(), errorCode.getMessage());

    }

}
