package com.webest.order.presentation.response;

import com.webest.order.domain.model.Order;
import com.webest.order.domain.model.OrderProduct;
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
                            String requestsToStore,
                            String requestsToRider,
                            Long storeAddressCode,
                            String storeDetailAddress,
                            Long arrivalAddressCode,
                            String arrivalDetailAddress,
                            Integer totalQuantity,
                            Double totalProductPrice,
                            Double couponAppliedAmount,
                            Double deliveryTipAmount,
                            Double totalPaymentPrice,
                            List<OrderProductResponse> orderProductResponses){


    public static OrderResponse of(Order order) {
        return new OrderResponse(
                order.getId(),
                order.getStoreId(),
                order.getPaymentId(),
                order.getCouponId(),
                order.getUserId(),
                order.getOrderStatus(),
                order.getIsRequest(),
                order.getRequestsToStore(),
                order.getRequestsToRider(),
                order.getStoreAddressCode(),
                order.getStoreDetailAddress(),
                order.getArrivalAddressCode(),
                order.getArrivalDetailAddress(),
                order.getTotalQuantity(),
                order.getTotalProductPrice(),
                order.getCouponAppliedAmount(),
                order.getDeliveryTipAmount(),
                order.getTotalPaymentPrice(),
                order.getOrderProducts().stream()
                        .map(OrderProductResponse::of)  // OrderProduct -> OrderProductResponse 변환
                        .collect(Collectors.toList())
        );
    }

    public static List<OrderResponse> of(List<Order> orders) {
        return orders.stream()
                .map(OrderResponse::of)
                .collect(Collectors.toList());
    }

}
