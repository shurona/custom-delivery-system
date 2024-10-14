package com.webest.order.application.dtos;

import com.webest.order.domain.model.OrderStatus;

import java.util.List;

public record OrderDto (Long storeId,
                        Long paymentId,
                        Long couponId,
                        Long userId,
                        OrderStatus orderStatus,
                        Boolean isRequest,
                        String requestsToStore,
                        String requestsToRider,
                        Integer totalQuantity,
                        Double totalProductPrice,
                        Double couponAppliedAmount,
                        Double deliveryTipAmount,
                        Double totalPaymentPrice,
                        List<OrderProductDto> orderProductDtos // 주문 상품 리스트 포함
) {
    public static OrderDto create(Long storeId,
                                  Long paymentId,
                                  Long couponId,
                                  Long userId,
                                  OrderStatus orderStatus,
                                  Boolean isRequest,
                                  String requestsToStore,
                                  String requestsToRider,
                                  Integer totalQuantity,
                                  Double totalProductPrice,
                                  Double couponAppliedAmount,
                                  Double deliveryTipAmount,
                                  Double totalPaymentPrice,
                                  List<OrderProductDto> orderProductDtos) {
        return new OrderDto(
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
                totalPaymentPrice,
                orderProductDtos);
    }

}
