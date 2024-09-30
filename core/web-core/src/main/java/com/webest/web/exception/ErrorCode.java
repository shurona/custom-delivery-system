package com.webest.web.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ErrorCode {

    SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 에러"),
    INVALID_INPUT(HttpStatus.BAD_REQUEST, "잘못된 입력입니다."),
    NOT_FOUND(HttpStatus.NOT_FOUND, "입력한 데이터가 없습니다."),

    // ORDER 관련 에러
    ORDER_NOT_FOUND(HttpStatus.NOT_FOUND, "주문을 찾을 수 없습니다."),
    ORDER_ALREADY_EXISTS(HttpStatus.CONFLICT, "이미 존재하는 주문입니다."),
    ORDER_CANNOT_BE_MODIFIED(HttpStatus.FORBIDDEN, "주문을 수정할 수 없습니다."),

    // ORDER_PRODUCT 관련 에러
    ORDER_PRODUCT_NOT_FOUND(HttpStatus.NOT_FOUND, "주문상품을 찾을 수 없습니다.");


    private HttpStatus status;
    private String message;

}
