package com.webest.order.presentation.request;


import com.webest.order.application.dtos.OrderDto;
import com.webest.order.domain.model.OrderStatus;

public record OrderUpdateRequest(Long storeId,
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
    public OrderDto toDto() {
        return OrderDto.create(
                this.storeId,
                this.paymentId,
                this.couponId,
                this.userId,
                this.orderStatus,
                this.isRequest,
                this.totalQuantity,
                this.totalProductPrice,
                this.couponAppliedAmount,
                this.deliveryTipAmount,
                this.totalPaymentPrice
        );
    }

}