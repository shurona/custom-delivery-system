package com.webest.store.product.presentation.dto;

public record CreateProductRequest(
        Long storeId,
        String name,
        Double price,
        String description
) {
}
