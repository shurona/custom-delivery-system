package com.webest.order.presentation.response;

import com.webest.order.infrastructure.model.OrderProductQuery;

import java.util.List;
import java.util.stream.Collectors;

public record OrderProductQueryResponse(Long productId,
                                        Integer quantity,
                                        Double price,
                                        Double totalPrice) {

    public static OrderProductQueryResponse of(OrderProductQuery orderProductQuery) {
        return new OrderProductQueryResponse(
                orderProductQuery.getProductId(),
                orderProductQuery.getQuantity(),
                orderProductQuery.getPrice(),
                orderProductQuery.getTotalPrice());
    }

    public static List<OrderProductQueryResponse> of(List<OrderProductQuery> orderProductQueries) {
        return orderProductQueries.stream()
                .map(OrderProductQueryResponse::of)
                .collect(Collectors.toList());
    }
}
