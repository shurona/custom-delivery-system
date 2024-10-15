package com.webest.order.domain.events;

import com.webest.order.application.dtos.OrderDto;
import com.webest.order.application.dtos.OrderProductDto;
import com.webest.order.domain.model.OrderProduct;
import com.webest.order.domain.model.OrderStatus;
import com.webest.order.presentation.request.orderproduct.OrderProductRequest;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderCreatedEvent {

    private Long id;

    private Long storeId;

    private Long paymentId;

    private Long couponId;

    private String userId;

    private OrderStatus orderStatus;

    private Boolean isRequest;

    private String requestsToStore;

    private String requestsToRider;

    private Long storeAddressCode;

    private String storeDetailAddress;

    private Long arrivalAddressCode;

    private String arrivalDetailAddress;

    private Integer totalQuantity;

    private Double totalProductPrice;

    private Double couponAppliedAmount;

    private Double deliveryTipAmount;

    private Double totalPaymentPrice;

    private List<OrderProductDto> orderProducts;

}
