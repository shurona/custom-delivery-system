package com.webest.store.store.exception;

import com.webest.store.category.exception.CategoryErrorCode;
import com.webest.web.exception.ApplicationException;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class StoreException extends ApplicationException {
    public StoreException(StoreErrorCode errorCode) {
        super(errorCode.getStatus(), errorCode.getMessage());
    }
}
