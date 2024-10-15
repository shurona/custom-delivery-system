package com.webest.store.product.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ProductStatus {
    AVAILABLE("주문 가능"), SOLD_OUT("품절");
    private final String description;
}
