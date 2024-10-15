package com.webest.order.presentation.response;

import com.webest.order.domain.model.OrderStatus;
import com.webest.order.infrastructure.model.OrderQuery;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public record OrderQueryResponse(String id,
                                 Long storeId,
                                 Long paymentId,
                                 Long couponId,
                                 String userId,
                                 OrderStatus orderStatus,
                                 Boolean isRequest,
                                 String requestsToStore,
                                 String requestsToRider,
                                 Long storeAddressCode,
                                 String storeDetailAddress,
                                 Long arrivalAddressCode,
                                 String arrivalDetailAddress,
                                 Integer totalQuantity,
                                 Double totalProductPrice,
                                 Double couponAppliedAmount,
                                 Double deliveryTipAmount,
                                 Double totalPaymentPrice,
                                 List<OrderProductQueryResponse> orderProductQueryResponses,
                                 LocalDateTime createdAt) {

    public static OrderQueryResponse of(OrderQuery orderQuery) {
        return new OrderQueryResponse(
                orderQuery.getId(),
                orderQuery.getStoreId(),
                orderQuery.getPaymentId(),
                orderQuery.getCouponId(),
                orderQuery.getUserId(),
                orderQuery.getOrderStatus(),
                orderQuery.getIsRequest(),
                orderQuery.getRequestsToStore(),
                orderQuery.getRequestsToRider(),
                orderQuery.getStoreAddressCode(),
                orderQuery.getStoreDetailAddress(),
                orderQuery.getArrivalAddressCode(),
                orderQuery.getArrivalDetailAddress(),
                orderQuery.getTotalQuantity(),
                orderQuery.getTotalProductPrice(),
                orderQuery.getCouponAppliedAmount(),
                orderQuery.getDeliveryTipAmount(),
                orderQuery.getTotalPaymentPrice(),
                orderQuery.getOrderProductQueries().stream()
                        .map(OrderProductQueryResponse::of)  // OrderProduct -> OrderProductResponse 변환
                        .collect(Collectors.toList()),
                orderQuery.getCreatedAt()
        );
    }

    public static List<OrderQueryResponse> of(List<OrderQuery> orderQueries) {
        return orderQueries.stream()
                .map(OrderQueryResponse::of)
                .collect(Collectors.toList());
    }
}
