package com.webest.order.application.dtos;

import com.webest.order.domain.model.OrderStatus;

import java.util.List;

public record OrderUpdateDto(Long storeId,
                             Long paymentId,
                             Long couponId,
                             String userId,
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
                             List<OrderProductDto> orderProductDtos // 주문 상품 리스트 포함
) {
    public static OrderUpdateDto create(Long storeId,
                                        Long paymentId,
                                        Long couponId,
                                        String userId,
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
                                        List<OrderProductDto> orderProductDtos) {
        return new OrderUpdateDto(storeId,
                                 paymentId,
                                 couponId,
                                 userId,
                                 orderStatus,
                                 isRequest,
                                requestsToStore,
                                 requestsToRider,
                                 storeAddressCode,
                                 storeDetailAddress,
                                 arrivalAddressCode,
                                 arrivalDetailAddress,
                                 totalQuantity,
                                 totalProductPrice,
                                 couponAppliedAmount,
                                 deliveryTipAmount,
                                 totalPaymentPrice,
                                 orderProductDtos);
    }

}

