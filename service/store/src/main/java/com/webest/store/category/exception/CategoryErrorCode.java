package com.webest.store.category.exception;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum CategoryErrorCode {
    CATEGORY_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 카데고리를 찾을 수 없습니다."),
    CATEGORY_ALREADY_EXISTS(HttpStatus.CONFLICT, "카테고리가 이미 존재합니다."),
    CATEGORY_INVALID_KEY(HttpStatus.BAD_REQUEST, "카테고리 키가 유효하지 않습니다");

    private final HttpStatus status;
    private final String message;
}
