package com.webest.store.store.api.dto;

public record UpdateStoreAddressRequest(
        Long storeId,
        String address // 주소를 입력받는 필드
) {}