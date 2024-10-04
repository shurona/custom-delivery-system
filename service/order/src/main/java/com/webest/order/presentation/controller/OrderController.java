package com.webest.order.presentation.controller;

import com.webest.order.application.service.OrderService;
import com.webest.order.presentation.request.order.OrderCreateRequest;
import com.webest.order.presentation.request.order.OrderSearchRequest;
import com.webest.order.presentation.request.order.OrderUpdateRequest;
import com.webest.order.presentation.response.OrderResponse;
import com.webest.web.common.CommonStaticVariable;
import com.webest.web.common.UserRole;
import com.webest.web.response.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.hibernate.query.Order;
import org.hibernate.query.Page;
import org.springframework.data.domain.PageRequest;
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
    public CommonResponse<OrderResponse> createOrder(@RequestHeader(name = CommonStaticVariable.X_USER_ID) Long userId,
                                                     @RequestHeader(name = CommonStaticVariable.X_USER_ROLE) UserRole userRole,
                                                     @RequestBody OrderCreateRequest createRequest) {


        return CommonResponse.success(orderService.createOrder(userId ,userRole, createRequest.toDto()));
    }

    /**
     * 주문 단건 조회
     */
    @GetMapping("/{orderId}")
    public CommonResponse<OrderResponse> getOrder(@RequestHeader(name = CommonStaticVariable.X_USER_ID) Long userId,
                                                  @RequestHeader(name = CommonStaticVariable.X_USER_ROLE) UserRole userRole,
                                                  @PathVariable(name = "orderId") Long orderId) {

        return CommonResponse.success(orderService.getOrder(orderId));
    }

    /**
     * 주문 전체 조회
     */
    @GetMapping
    public CommonResponse<List<OrderResponse>> getAllOrders(@RequestHeader(name = CommonStaticVariable.X_USER_ID) Long userId,
                                                            @RequestHeader(name = CommonStaticVariable.X_USER_ROLE) UserRole userRole) {

        return CommonResponse.success(orderService.getAllOrders());
    }

    /**
     * 주문 수정
     */
    @PatchMapping("/{orderId}")
    public CommonResponse<OrderResponse> updateOrder(@RequestHeader(name = CommonStaticVariable.X_USER_ID) Long userId,
                                                     @RequestHeader(name = CommonStaticVariable.X_USER_ROLE) UserRole userRole,
                                                     @PathVariable(name = "orderId") Long orderId,
                                                     @RequestBody OrderUpdateRequest updateRequest) {

        return CommonResponse.success(orderService.updateOrder(userId, userRole, orderId, updateRequest.toDto()));
    }

    /**
     * 주문 삭제
     */
    @DeleteMapping("/{orderId}")
    public CommonResponse<OrderResponse> deleteOrder(@RequestHeader(name = CommonStaticVariable.X_USER_ID) Long userId,
                                                     @RequestHeader(name = CommonStaticVariable.X_USER_ROLE) UserRole userRole,
                                                     @PathVariable(name = "orderId") Long orderId) {

        orderService.deleteOrder(userId, userRole, orderId);

        return CommonResponse.success(null);
    }

    /**
     * 주문 확인 (주문확인중: CONFIRMING_ORDER -> 준비중: PREPARING)
     */
    @PostMapping("/{orderId}/preparing")
    public CommonResponse<?> preparingOrder(@RequestHeader(name = CommonStaticVariable.X_USER_ID) Long userId,
                                                      @RequestHeader(name = CommonStaticVariable.X_USER_ROLE) UserRole userRole,
                                                      @PathVariable(name = "orderId") Long orderId) {

        orderService.preparing(userId, userRole, orderId);

        return CommonResponse.success(null);
    }

    /**
     * 주문 요청 (주문 -> 배달)
     */
    @PostMapping("/{orderId}/request")
    public CommonResponse<OrderResponse> requestOrder(@RequestHeader(name = CommonStaticVariable.X_USER_ID) Long userId,
                                                      @RequestHeader(name = CommonStaticVariable.X_USER_ROLE) UserRole userRole,
                                                      @PathVariable(name = "orderId") Long orderId) {

        return CommonResponse.success(orderService.requestOrder(userId, userRole, orderId));
    }


    /**
     * 주문 검색
     */
    @GetMapping("/search")
    public CommonResponse<?> searchOrders(@RequestHeader(name = CommonStaticVariable.X_USER_ID) Long userId,
                                          @RequestHeader(name = CommonStaticVariable.X_USER_ROLE) UserRole userRole,
                                          @RequestBody OrderSearchRequest orderSearchRequest,
                                          @RequestParam(defaultValue = "1") int page,
                                          @RequestParam(defaultValue = "20") int size) {

        PageRequest pageRequest = PageRequest.of(page - 1, size);

        return CommonResponse.success(orderService.searchOrders(userId, userRole, orderSearchRequest.toDto(), pageRequest));
    }


}

