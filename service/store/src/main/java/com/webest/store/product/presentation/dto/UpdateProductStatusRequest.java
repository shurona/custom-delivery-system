package com.webest.store.product.presentation.dto;

import com.webest.store.product.domain.ProductStatus;

public record UpdateProductStatusRequest(Long productId, ProductStatus status) {
}
