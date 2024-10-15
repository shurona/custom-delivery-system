package com.webest.web.exception;

import static com.webest.web.exception.ErrorCode.INVALID_INPUT;
import static com.webest.web.exception.ErrorCode.SERVER_ERROR;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

import com.webest.web.exception.custom.InvalidMethodResponseDto;
import com.webest.web.response.CommonResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<? extends CommonResponse<?>> applicationException(
        ApplicationException exception
    ) {

        return ResponseEntity.status(exception.getHttpStatus())
            .body(CommonResponse.error(exception.getHttpStatus(), exception.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<? extends CommonResponse<?>> handlerValidationException(
        MethodArgumentNotValidException exception) {

        if (exception.getCause() instanceof ApplicationException) {
            return ResponseEntity.status(BAD_REQUEST)
                .body(CommonResponse.error(INVALID_INPUT.getStatus(), INVALID_INPUT.getMessage()));
        }

        List<InvalidMethodResponseDto> invalidInputResList = exception.getBindingResult()
            .getFieldErrors()
            .stream()
            .map(ex -> new InvalidMethodResponseDto(ex.getField(),
                ex.getDefaultMessage())) // defaultMessage 필드명을 message 변경
            .toList();

        return ResponseEntity.status(BAD_REQUEST)
            .body(CommonResponse.error(INVALID_INPUT.getStatus(), INVALID_INPUT.getMessage(),
                invalidInputResList));
    }

    @ExceptionHandler({Exception.class, RuntimeException.class})
    public ResponseEntity<? extends CommonResponse<?>> handleException(Exception e) {

        // 예상 치 못한 에러
        log.error("예상치 못한 에러 : ", e);

        return ResponseEntity.status(SERVER_ERROR.getStatus())
            .body(CommonResponse.error(SERVER_ERROR.getStatus(), SERVER_ERROR.getMessage()));
    }

}
