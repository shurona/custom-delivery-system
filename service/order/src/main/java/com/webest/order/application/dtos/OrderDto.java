package com.webest.order.application.dtos;

import com.webest.order.domain.model.Order;
import com.webest.order.domain.model.OrderStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;

public record OrderDto (Long storeId,
                        Long paymentId,
                        Long couponId,
                        Long userId,
                        OrderStatus orderStatus,
                        Boolean isRequest,
                        Integer totalQuantity,
                        Double totalProductPrice,
                        Double couponAppliedAmount,
                        Double deliveryTipAmount,
                        Double totalPaymentPrice
) {
    public static OrderDto create(Long storeId,
                                  Long paymentId,
                                  Long couponId,
                                  Long userId,
                                  OrderStatus orderStatus,
                                  Boolean isRequest,
                                  Integer totalQuantity,
                                  Double totalProductPrice,
                                  Double couponAppliedAmount,
                                  Double deliveryTipAmount,
                                  Double totalPaymentPrice) {
        return new OrderDto(
                storeId,
                paymentId,
                couponId,
                userId,
                orderStatus,
                isRequest,
                totalQuantity,
                totalProductPrice,
                couponAppliedAmount,
                deliveryTipAmount,
                totalPaymentPrice);
    }

}
