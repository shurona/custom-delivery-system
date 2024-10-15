package com.webest.order.domain.events;

import com.webest.order.domain.model.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderPaymentCompletedEvent {

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
}
