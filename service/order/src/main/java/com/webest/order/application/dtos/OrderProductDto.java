package com.webest.order.application.dtos;


public record OrderProductDto(Long productId,
                              Integer quantity,
                              Double price,
                              Double totalPrice
) {
    public static OrderProductDto create(Long productId,
                                         Integer quantity,
                                         Double price,
                                         Double totalPrice
    ) {
        return new OrderProductDto(
                productId,
                quantity,
                price,
                totalPrice
        );
    }

}
