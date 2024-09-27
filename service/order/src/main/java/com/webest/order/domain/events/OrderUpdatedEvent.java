package com.webest.order.domain.events;

import com.webest.order.domain.model.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderUpdatedEvent {

    private Long id;

    private Long storeId;

    private Long paymentId;

    private Long couponId;

    private Long userId;

    private OrderStatus orderStatus;

    private Boolean isRequest;

    private String requests;

    private Integer totalQuantity;

    private Double totalProductPrice;

    private Double couponAppliedAmount;

    private Double deliveryTipAmount;

    private Double totalPaymentPrice;
}
