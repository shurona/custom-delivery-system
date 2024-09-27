package com.webest.order.application.service;

import com.webest.order.application.dtos.OrderDto;
import com.webest.order.domain.model.Order;
import com.webest.order.domain.repository.OrderRepository;
import com.webest.order.presentation.response.OrderResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    public OrderResponse createOrder(OrderDto request) {

        Order order = Order.create(
                request.storeId(),
                request.paymentId(),
                request.couponId(),
                request.userId(),
                request.orderStatus(),
                request.isRequest(),
                request.totalQuantity(),
                request.totalProductPrice(),
                request.couponAppliedAmount(),
                request.deliveryTipAmount(),
                request.totalPaymentPrice()
        );

        return OrderResponse.of(orderRepository.save(order));
    }

}

