package com.webest.order.application.dtos;

import com.webest.order.domain.model.OrderStatus;

import java.util.List;

public record OrderSearchDto(
        Long orderId,
        Long storeId,
        Long paymentId,
        Long couponId,
        String userId,
        OrderStatus orderStatus,
        Boolean isRequest,
        String requestsToStore,
        String requestsToRider,
        Integer totalQuantity,
        Double totalProductPrice,
        Double couponAppliedAmount,
        Double deliveryTipAmount,
        Double totalPaymentPrice
) {
public static OrderSearchDto create(Long orderId,
                                    Long storeId,
                                    Long paymentId,
                                    Long couponId,
                                    String userId,
                                    OrderStatus orderStatus,
                                    Boolean isRequest,
                                    String requestsToStore,
                                    String requestsToRider,
                                    Integer totalQuantity,
                                    Double totalProductPrice,
                                    Double couponAppliedAmount,
                                    Double deliveryTipAmount,
                                    Double totalPaymentPrice) {
    return new OrderSearchDto(
            orderId,
            storeId,
            paymentId,
            couponId,
            userId,
            orderStatus,
            isRequest,
            requestsToStore,
            requestsToRider,
            totalQuantity,
            totalProductPrice,
            couponAppliedAmount,
            deliveryTipAmount,
            totalPaymentPrice);
}

}