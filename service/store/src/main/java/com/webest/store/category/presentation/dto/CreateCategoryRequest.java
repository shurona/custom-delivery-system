package com.webest.store.category.presentation.dto;

import com.webest.store.category.domain.model.StoreCategory;

public record CreateCategoryRequest(String key, String value) {
    public StoreCategory toEntity() {
        return StoreCategory.of(key, value);
    }
}
