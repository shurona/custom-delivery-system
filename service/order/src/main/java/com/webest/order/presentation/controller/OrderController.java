package com.webest.order.presentation.controller;

import com.webest.order.application.service.OrderService;
import com.webest.order.presentation.request.OrderCreateRequest;
import com.webest.order.presentation.response.OrderResponse;
import com.webest.web.response.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/orders")
public class OrderController {

    private final OrderService orderService;


    @PostMapping
    public CommonResponse<OrderResponse> createOrder(@RequestBody OrderCreateRequest createRequest) {

            OrderResponse orderResponse = orderService.createOrder(createRequest.toDto());

        return CommonResponse.success(orderResponse);
    }
}
