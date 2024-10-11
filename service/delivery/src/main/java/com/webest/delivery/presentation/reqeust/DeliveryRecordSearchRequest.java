package com.webest.delivery.presentation.reqeust;

import com.webest.delivery.application.dtos.DeliveryDto;
import com.webest.delivery.application.dtos.DeliveryRecordDto;
import com.webest.delivery.application.dtos.DeliveryRecordSearchDto;
import com.webest.delivery.domain.model.DeliveryStatus;

import java.time.LocalDateTime;

public record DeliveryRecordSearchRequest(Long deliveryId,
                                          Long riderId,
                                          Long orderId,
                                          DeliveryStatus deliveryStatus,
                                          Double deliveryFeeAmount,
                                          LocalDateTime createdAt,
                                          Long createdBy) {

    public DeliveryRecordSearchDto toDto() {
        return DeliveryRecordSearchDto.create(
                this.deliveryId,
                this.orderId,
                this.riderId,
                this.deliveryStatus,
                this.deliveryFeeAmount,
                this.createdAt,
                this.createdBy
        );
    }
}
