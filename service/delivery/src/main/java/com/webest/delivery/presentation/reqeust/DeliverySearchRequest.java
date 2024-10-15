package com.webest.delivery.presentation.reqeust;

import com.webest.delivery.application.dtos.DeliveryDto;
import com.webest.delivery.domain.model.DeliveryStatus;

public record DeliverySearchRequest(Long orderId,
                                    String riderId,
                                    String requestsToRider,
                                    DeliveryStatus deliveryStatus,
                                    Long storeAddressCode,
                                    String storeDetailAddress,
                                    Long arrivalAddressCode,
                                    String arrivalDetailAddress,
                                    Double deliveryFeeAmount) {


    public DeliveryDto toDto() {
        return DeliveryDto.create(
                this.orderId,
                this.riderId,
                this.requestsToRider,
                this.deliveryStatus,
                this.storeAddressCode,
                this.storeDetailAddress,
                this.arrivalAddressCode,
                this.arrivalDetailAddress,
                this.deliveryFeeAmount
        );
    }
}
