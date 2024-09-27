package com.webest.order.presentation.response;

import com.webest.order.domain.model.Order;
import com.webest.order.domain.model.OrderStatus;

import java.util.List;
import java.util.stream.Collectors;

public record OrderResponse(Long id,
                            Long storeId,
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


    public static OrderResponse of(Order order) {
        return new OrderResponse(
                order.getId(),
                order.getStoreId(),
                order.getPaymentId(),
                order.getCouponId(),
                order.getUserId(),
                order.getOrderStatus(),
                order.getIsRequest(),
                order.getTotalQuantity(),
                order.getTotalProductPrice(),
                order.getCouponAppliedAmount(),
                order.getDeliveryTipAmount(),
                order.getTotalPaymentPrice());
    }

    public static List<OrderResponse> of(List<Order> orders) {
        return orders.stream()
                .map(OrderResponse::of)
                .collect(Collectors.toList());
    }

}
