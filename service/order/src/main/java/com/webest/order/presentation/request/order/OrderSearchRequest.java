package com.webest.order.presentation.request.order;

import com.webest.order.application.dtos.OrderDto;
import com.webest.order.application.dtos.OrderSearchDto;
import com.webest.order.domain.model.OrderStatus;
import com.webest.order.presentation.request.orderproduct.OrderProductRequest;

import java.util.List;
import java.util.stream.Collectors;

public record OrderSearchRequest(Long storeId,
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

    public OrderSearchDto toDto() {
        return OrderSearchDto.create(
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
                this.totalPaymentPrice);
    }
}
