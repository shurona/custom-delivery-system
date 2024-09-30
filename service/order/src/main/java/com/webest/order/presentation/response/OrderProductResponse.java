package com.webest.order.presentation.response;

import com.webest.order.domain.model.Order;
import com.webest.order.domain.model.OrderProduct;

import java.util.List;
import java.util.stream.Collectors;

public record OrderProductResponse(Long id,
                                   Long productId,
                                   Integer quantity,
                                   Double price,
                                   Double totalPrice) {

    public static OrderProductResponse of(OrderProduct orderProduct) {
        return new OrderProductResponse(
                orderProduct.getId(),
                orderProduct.getProductId(),
                orderProduct.getQuantity(),
                orderProduct.getPrice(),
                orderProduct.getTotalPrice());
    }

    public static List<OrderProductResponse> of(List<OrderProduct> orderProducts) {
        return orderProducts.stream()
                .map(OrderProductResponse::of)
                .collect(Collectors.toList());
    }
}
