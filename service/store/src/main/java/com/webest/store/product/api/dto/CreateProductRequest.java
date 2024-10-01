package com.webest.store.product.api.dto;

import com.webest.store.product.domain.Product;

public record CreateProductRequest(
        Long storeId,
        String name,
        Double price,
        String description
) {
    public Product toEntity() {
        return Product.of(storeId, name, price, description);
    }
}
