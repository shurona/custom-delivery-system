package com.webest.delivery.presentation.response;

import com.webest.delivery.domain.model.DeliveryRecord;
import com.webest.delivery.domain.model.DeliveryStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public record DeliveryRecordResponse(Long id,
                                     Long deliveryId,
                                     String riderId,
                                     Long orderId,
                                     DeliveryStatus deliveryStatus,
                                     Double deliveryFeeAmount,
                                     LocalDateTime createdAt) {

    public static DeliveryRecordResponse of(DeliveryRecord deliveryRecord) {
        return new DeliveryRecordResponse(
                deliveryRecord.getId(),
                deliveryRecord.getDeliveryId(),
                deliveryRecord.getRiderId(),
                deliveryRecord.getOrderId(),
                deliveryRecord.getDeliveryStatus(),
                deliveryRecord.getDeliveryFeeAmount(),
                deliveryRecord.getCreatedAt());
    }

    public static List<DeliveryRecordResponse> of(List<DeliveryRecord> deliveryRecords) {
        return deliveryRecords.stream()
                .map(DeliveryRecordResponse::of)
                .collect(Collectors.toList());
    }
}
