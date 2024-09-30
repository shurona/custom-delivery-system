package com.webest.store.category.exception;

import com.webest.web.exception.ApplicationException;
import com.webest.web.exception.ErrorCode;
import lombok.Getter;

@Getter
public class CategoryException extends ApplicationException {

    public CategoryException(CategoryErrorCode errorCode) {
        super(errorCode.getStatus(), errorCode.getMessage());
    }
}
