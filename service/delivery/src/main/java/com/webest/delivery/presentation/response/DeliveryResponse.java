package com.webest.delivery.presentation.response;

import com.webest.delivery.domain.model.Delivery;
import com.webest.delivery.domain.model.DeliveryStatus;

import java.util.List;
import java.util.stream.Collectors;

public record DeliveryResponse(Long id,
                               Long orderId,
                               Long riderId,
                               String requestsToRider,
                               DeliveryStatus deliveryStatus,
                               Long storeAddressCode,
                               String storeDetailAddress,
                               Long arrivalAddressCode,
                               String arrivalDetailAddress,
                               Double deliveryFeeAmount) {

    public static DeliveryResponse of(Delivery delivery) {
        return new DeliveryResponse(
                delivery.getId(),
                delivery.getOrderId(),
                delivery.getRiderId(),
                delivery.getRequestsToRider(),
                delivery.getDeliveryStatus(),
                delivery.getStoreAddressCode(),
                delivery.getStoreDetailAddress(),
                delivery.getArrivalAddressCode(),
                delivery.getArrivalDetailAddress(),
                delivery.getDeliveryFeeAmount());
    }

    public static List<DeliveryResponse> of(List<Delivery> deliveries) {
        return deliveries.stream()
                .map(DeliveryResponse::of)
                .collect(Collectors.toList());
    }
}
