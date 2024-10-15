package com.webest.delivery.application.dtos;

import com.webest.delivery.domain.model.DeliveryStatus;

public record DeliveryRecordDto(Long deliveryId,
                                String riderId,
                                Long orderId,
                                DeliveryStatus deliveryStatus,
                                Double deliveryFeeAmount) {

    public static DeliveryRecordDto create(Long deliveryId,
                                           String riderId,
                                           Long orderId,
                                           DeliveryStatus deliveryStatus,
                                           Double deliveryFeeAmount) {
        return new DeliveryRecordDto(
                deliveryId,
                riderId,
                orderId,
                deliveryStatus,
                deliveryFeeAmount);
    }
}
