package com.webest.order.presentation.controller;

import com.webest.order.application.service.OrderService;
import com.webest.order.presentation.request.OrderCreateRequest;
import com.webest.order.presentation.request.OrderUpdateRequest;
import com.webest.order.presentation.response.OrderResponse;
import com.webest.web.response.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/orders")
public class OrderController {

    private final OrderService orderService;

    /**
     * 주문 생성
     */
    @PostMapping
    public CommonResponse<OrderResponse> createOrder(@RequestBody OrderCreateRequest createRequest) {

        return CommonResponse.success(orderService.createOrder(createRequest.toDto()));
    }

    /**
     * 주문 단건 조회
     */
    @GetMapping("/{orderId}")
    public CommonResponse<OrderResponse> getOrder(@PathVariable(name = "orderId") Long orderId) {

        return CommonResponse.success(orderService.getOrder(orderId));
    }

    /**
     * 주문 전체 조회
     */
    @GetMapping
    public CommonResponse<List<OrderResponse>> getAllOrders() {

        return CommonResponse.success(orderService.getAllOrders());
    }

    /**
     * 주문 수정
     */
    @PatchMapping("/{orderId}")
    public CommonResponse<OrderResponse> updateOrder(@PathVariable(name = "orderId") Long orderId,
                                                     @RequestBody OrderUpdateRequest updateRequest) {

        return CommonResponse.success(orderService.updateOrder(orderId, updateRequest.toDto()));
    }

    /**
     * 주문 삭제
     */
    @DeleteMapping("/{orderId}")
    public CommonResponse<OrderResponse> deleteOrder(@PathVariable(name = "orderId") Long orderId) {

        orderService.deleteOrder(orderId);

        return CommonResponse.success(null);
    }


    /**
     * 주문
     */


}

