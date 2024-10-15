package com.webest.order.infrastructure.model;

import com.webest.order.application.dtos.OrderProductDto;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderProductQuery {

    private Long productId;

    private Integer quantity;

    private Double price;

    private Double totalPrice;
    
    public static OrderProductQuery of(OrderProductDto orderProductDto) {
        return new OrderProductQuery(
                orderProductDto.productId(),
                orderProductDto.quantity(),
                orderProductDto.price(),
                orderProductDto.totalPrice());
    }

    public static List<OrderProductQuery> of(List<OrderProductDto> orders) {
        return orders.stream()
                .map(OrderProductQuery::of)
                .collect(Collectors.toList());
    }

}
