package com.webest.store.store.api.dto;

import com.webest.store.store.domain.Store;
import com.webest.store.store.domain.StoreStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

public record CreateStoreRequest(
        String name,
        Long ownerId,
        Long categoryId,
        Integer preparationTime,
        Double minimumOrderAmount,
        String phone,
        LocalTime openTime,
        LocalTime closeTime
) {
    public Store toEntity() {
        return Store.of(name, ownerId, categoryId, preparationTime, minimumOrderAmount, phone, openTime, closeTime);
    }
}

