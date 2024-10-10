package com.webest.delivery.domain.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ErrorCode {

    // DELIVERY 관련 에러
    DELIVERY_NOT_FOUND(HttpStatus.NOT_FOUND, "배달을 찾을 수 없습니다."),
    DELIVERY_ALREADY_EXISTS(HttpStatus.CONFLICT, "이미 존재하는 주문입니다."),
    DELIVERY_CANNOT_BE_MODIFIED(HttpStatus.FORBIDDEN, "주문을 수정할 수 없습니다."),
    INVALID_ORDER_STATUS(HttpStatus.BAD_REQUEST, "배달 상태가 잘못되었습니다.");


    private final HttpStatus status;
    private final String message;
}
