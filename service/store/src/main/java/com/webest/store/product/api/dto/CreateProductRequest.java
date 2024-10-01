package com.webest.store.product.api.dto;

public record CreateProductRequest(
        Long storeId,
        String name,
        Double price,
        String description
) {
}
