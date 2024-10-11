package com.webest.store.store.presentation.dto;

import com.webest.store.store.domain.model.Store;
import com.webest.store.store.domain.model.StoreAddress;
import com.webest.store.store.domain.model.StoreStatus;

import java.time.LocalTime;
import java.util.List;

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
        StoreAddress storeAddress,
        Double latitude,
        Double longitude,
        Double deliveryRadius,
        Double deliveryTip,
        List<Long> addressCodeList
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
                store.getStoreAddress(),
                store.getLatitude(),
                store.getLongitude(),
                store.getDeliveryRadius(),
                store.getDeliveryTip(),
                store.getAddressCodeList()
        );
    }
}

