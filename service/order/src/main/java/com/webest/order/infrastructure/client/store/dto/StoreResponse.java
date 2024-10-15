package com.webest.order.infrastructure.client.store.dto;

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

}