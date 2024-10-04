package com.webest.store.store.presentation.dto;

import com.webest.store.store.domain.Store;
import com.webest.store.store.domain.StoreStatus;

import java.time.LocalTime;

public record StoreResponse(
        Long id,
        String name,
        Long ownerId,
        Long categoryId,
        Integer preparationTime,
        Double minimumOrderAmount,
        StoreStatus status,
        String phone,
        LocalTime openTime,
        LocalTime closeTime,
        String address,
        Double latitude,
        Double longitude,
        Double deliveryRadius,
        Double deliveryTip
) {
    public static StoreResponse of(Store store) {
        return new StoreResponse(
                store.getId(),
                store.getName(),
                store.getOwnerId(),
                store.getCategoryId(),
                store.getPreparationTime(),
                store.getMinimumOrderAmount(),
                store.getStatus(),
                store.getPhone(),
                store.getOpenTime(),
                store.getCloseTime(),
                store.getAddress(),
                store.getLatitude(),
                store.getLongitude(),
                store.getDeliveryRadius(),
                store.getDeliveryTip()
        );
    }
}

