package com.webest.store.store.presentation.dto;

public record UpdateStoreAddressRequest(
        Long storeId,
        String address // 주소를 입력받는 필드
) {}