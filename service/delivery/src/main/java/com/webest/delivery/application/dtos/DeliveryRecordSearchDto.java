package com.webest.delivery.application.dtos;

import com.webest.delivery.domain.model.DeliveryStatus;
import java.time.LocalDateTime;

public record DeliveryRecordSearchDto(Long deliveryId,
                                      String riderId,
                                      Long orderId,
                                      DeliveryStatus deliveryStatus,
                                      Double deliveryFeeAmount,
                                      LocalDateTime createdAt,
                                      String createdBy) {

    public static DeliveryRecordSearchDto create(Long deliveryId,
        String riderId,
        Long orderId,
        DeliveryStatus deliveryStatus,
        Double deliveryFeeAmount,
        LocalDateTime createdAt,
        String createdBy) {
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
