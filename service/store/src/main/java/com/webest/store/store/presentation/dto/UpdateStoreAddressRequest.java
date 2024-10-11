package com.webest.store.store.presentation.dto;

public record UpdateStoreAddressRequest(
        Long storeId,
        String city,                    // 시
        String street,                 // 구
        String district,              // 동
        String detailAddress
) {}