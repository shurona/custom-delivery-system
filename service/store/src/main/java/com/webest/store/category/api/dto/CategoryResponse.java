package com.webest.store.category.api.dto;

import com.webest.store.category.domain.StoreCategory;

public record CategoryResponse(Long id, String key, String value) {
    public static CategoryResponse of (StoreCategory storeCategory) {
        return new CategoryResponse(storeCategory.getId(), storeCategory.getCategoryKey(), storeCategory.getValue());
    }
}
