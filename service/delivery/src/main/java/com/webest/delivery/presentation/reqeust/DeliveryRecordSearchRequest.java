package com.webest.delivery.presentation.reqeust;

import com.webest.delivery.application.dtos.DeliveryRecordSearchDto;
import com.webest.delivery.domain.model.DeliveryStatus;
import java.time.LocalDateTime;

public record DeliveryRecordSearchRequest(Long deliveryId,
                                          String riderId,
                                          Long orderId,
                                          DeliveryStatus deliveryStatus,
                                          Double deliveryFeeAmount,
                                          LocalDateTime createdAt,
                                          String createdBy) {

    public DeliveryRecordSearchDto toDto() {
        return DeliveryRecordSearchDto.create(
            this.deliveryId,
            this.riderId,
            this.orderId,
            this.deliveryStatus,
            this.deliveryFeeAmount,
            this.createdAt,
            this.createdBy
        );
    }
}
