package com.webest.web.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public class CommonResponse<T> {

    private int status;
    private String message;
    private T data;

    /**
     * 성공 시 메서드
     */
    public static <T> CommonResponse<T> success(T data) {
        return new CommonResponse<>(200, "Success", data);
    }

    /**
     * 실패시 메서드 - data 필드가 필요 없는 경우
     */
    public static CommonResponse<?> error(HttpStatus status, String message) {

        return new CommonResponse<>(status.value(), message, null);
    }

    /**
     * 실패시 메서드 - data 필드가 필요한 경우
     */
    public static <T> CommonResponse<T> error(HttpStatus status, String message, T data) {
        return new CommonResponse<>(status.value(), message, data);
    }

}
