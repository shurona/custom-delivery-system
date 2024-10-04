package com.webest.store.product.presentation.dto;

import com.webest.store.product.domain.Product;
import com.webest.store.product.domain.ProductStatus;

public record ProductResponse(
        Long id,
        Long storeId,
        String name,
        Double price,
        String description,
        ProductStatus status
) {
    public static ProductResponse of(Product product) {
        return new ProductResponse(
                product.getId(),
                product.getStore().getId(),
                product.getName(),
                product.getPrice(),
                product.getDescription(),
                product.getStatus()
        );
    }
}