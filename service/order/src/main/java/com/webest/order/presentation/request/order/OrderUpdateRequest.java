package com.webest.order.presentation.request.order;


import com.webest.order.application.dtos.OrderDto;
import com.webest.order.application.dtos.OrderUpdateDto;
import com.webest.order.domain.model.OrderProduct;
import com.webest.order.domain.model.OrderStatus;
import com.webest.order.presentation.request.orderproduct.OrderProductRequest;

import java.util.List;
import java.util.stream.Collectors;

public record OrderUpdateRequest(Long storeId,
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
                                 List<OrderProductRequest> orderProductRequests) {
    public OrderUpdateDto toDto() {
        return OrderUpdateDto.create(
                this.storeId,
                this.paymentId,
                this.couponId,
                this.userId,
                this.orderStatus,
                this.isRequest,
                this.requestsToStore,
                this.requestsToRider,
                this.storeAddressCode,
                this.storeDetailAddress,
                this.arrivalAddressCode,
                this.arrivalDetailAddress,
                this.totalQuantity,
                this.totalProductPrice,
                this.couponAppliedAmount,
                this.deliveryTipAmount,
                this.totalPaymentPrice,
                this.orderProductRequests.stream()
                        .map(OrderProductRequest::toDto)
                        .collect(Collectors.toList())
        );
    }

}