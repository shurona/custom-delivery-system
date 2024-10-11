package com.webest.delivery.application.dtos;

import com.webest.delivery.domain.model.DeliveryStatus;

import java.time.LocalDateTime;

public record DeliveryRecordSearchDto(Long deliveryId,
                                      Long riderId,
                                      Long orderId,
                                      DeliveryStatus deliveryStatus,
                                      Double deliveryFeeAmount,
                                      LocalDateTime createdAt,
                                      Long createdBy) {

    public static DeliveryRecordSearchDto create(Long deliveryId,
                                           Long riderId,
                                           Long orderId,
                                           DeliveryStatus deliveryStatus,
                                           Double deliveryFeeAmount,
                                           LocalDateTime createdAt,
                                           Long createdBy) {
        return new DeliveryRecordSearchDto(
                deliveryId,
                riderId,
                orderId,
                deliveryStatus,
                deliveryFeeAmount,
                createdAt,
                createdBy);
    }
}
