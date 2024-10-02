package com.webest.coupon.common.exception;

import org.springframework.http.HttpStatus;

public enum CouponErrorCode {
    // 400
    INVALID_INPUT(HttpStatus.BAD_REQUEST, "잘못된 입력입니다."),
    COUPON_PERCENTAGE_OVER_90(HttpStatus.BAD_REQUEST, "퍼센트 쿠폰은 90을 넘길 수 없습니다."),
    STARTTIME_AFTER_ENDTIME(HttpStatus.BAD_REQUEST, "시작 날짜가 끝 날짜를 넘을 수 없습니다."),
    CHECK_UNDER_ZERO(HttpStatus.BAD_REQUEST, "0 이상이어야 합니다.."),

    // 404
    COUPON_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 쿠폰 정보입니다."),

    // 500
    DB_CONVERTING_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "DB 타입 변환 과정에서 오류 발생"),
    ;

    private HttpStatus status;
    private String message;

    CouponErrorCode(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
