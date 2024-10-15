package com.webest.store.product.presentation.dto;

public record UpdateProductDetailsRequest(Long productId, String name, Double price, String description) {
}
