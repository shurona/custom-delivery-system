package com.webest.delivery.application.dtos;

import com.webest.delivery.domain.model.DeliveryStatus;

import java.util.List;

public record DeliveryDto(Long orderId,
                          Long riderId,
                          String requestsToRider,
                          DeliveryStatus deliveryStatus,
                          Long storeAddressCode,
                          String storeDetailAddress,
                          Long arrivalAddressCode,
                          String arrivalDetailAddress,
                          Double deliveryFeeAmount) {

    public static DeliveryDto create(Long orderId,
                                     Long riderId,
                                     String requestsToRider,
                                     DeliveryStatus deliveryStatus,
                                     Long storeAddressCode,
                                     String storeDetailAddress,
                                     Long arrivalAddressCode,
                                     String arrivalDetailAddress,
                                     Double deliveryFeeAmount) {
        return new DeliveryDto(
                orderId,
                riderId,
                requestsToRider,
                deliveryStatus,
                storeAddressCode,
                storeDetailAddress,
                arrivalAddressCode,
                arrivalDetailAddress,
                deliveryFeeAmount);
    }
}
