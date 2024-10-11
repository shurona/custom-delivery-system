package com.webest.order.presentation.request.orderproduct;

import com.webest.order.application.dtos.OrderProductDto;

public record OrderProductRequest(Long productId,
                                        Integer quantity,
                                        Double price,
                                        Double totalPrice) {

    public OrderProductDto toDto() {
        return OrderProductDto.create(
                this.productId,
                this.quantity,
                this.price,
                this.totalPrice
        );
    }

}