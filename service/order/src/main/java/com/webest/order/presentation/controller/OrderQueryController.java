package com.webest.order.presentation.controller;

import com.webest.order.application.service.OrderQueryService;
import com.webest.order.presentation.request.order.OrderSearchRequest;
import com.webest.order.presentation.response.OrderQueryResponse;
import com.webest.web.common.CommonStaticVariable;
import com.webest.web.common.UserRole;
import com.webest.web.response.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/order-queries")
public class OrderQueryController {

    private final OrderQueryService orderQueryService;

    @GetMapping("/{orderId}")
    public CommonResponse<OrderQueryResponse> getOrder(@RequestHeader(name = CommonStaticVariable.X_USER_ID) Long userId,
                                                       @RequestHeader(name = CommonStaticVariable.X_USER_ROLE) UserRole userRole,
                                                       @PathVariable(name = "orderId") Long orderId) {

        return CommonResponse.success(orderQueryService.getOrderQuery(orderId));
    }

    @GetMapping
    public CommonResponse<?> getAllOrders(@RequestHeader(name = CommonStaticVariable.X_USER_ID) Long userId,
                                          @RequestHeader(name = CommonStaticVariable.X_USER_ROLE) UserRole userRole) {

        return CommonResponse.success(orderQueryService.getAllOrderQueries());
    }

    @GetMapping("/search")
    public CommonResponse<?> searchOrders(@RequestHeader(name = CommonStaticVariable.X_USER_ID) Long userId,
                                          @RequestHeader(name = CommonStaticVariable.X_USER_ROLE) UserRole userRole,
                                          @RequestBody OrderSearchRequest orderSearchRequest,
                                          @RequestParam(defaultValue = "1") int page,
                                          @RequestParam(defaultValue = "20") int size) {
        PageRequest pageRequest = PageRequest.of(page - 1, size);


        return CommonResponse.success(orderQueryService.searchOrders(userId, userRole, orderSearchRequest.toDto(), pageRequest));
    }

}
