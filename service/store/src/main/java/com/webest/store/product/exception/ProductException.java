package com.webest.store.product.exception;

import com.webest.web.exception.ApplicationException;
import lombok.Getter;

@Getter
public class ProductException extends ApplicationException {
    public ProductException(ProductErrorCode errorCode) {
        super(errorCode.getStatus(), errorCode.getMessage());
    }
}
