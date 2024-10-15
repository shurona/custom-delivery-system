package com.webest.coupon.common.exception;

import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
public class CustomFeignErrorDecoder implements ErrorDecoder {

    private final ErrorDecoder defaultErrorDecoder = new Default();

    @Override
    public Exception decode(String methodKey, Response response) {
        HttpStatus status = HttpStatus.valueOf(response.status());

        switch (status) {
            case BAD_REQUEST:
            case NOT_FOUND:
                return new CouponException(CouponErrorCode.INVALID_INPUT);
            case INTERNAL_SERVER_ERROR: // todo: 이 부분 외부 노출 고민
                return new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "서버 오류가 발생했습니다.");
            default:
                return defaultErrorDecoder.decode(methodKey, response);
        }
    }
}

