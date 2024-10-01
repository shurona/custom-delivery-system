package com.webest.order.domain.model;

public enum OrderStatus {
    PAYMENT_COMPLETED,    // 결제 완료
    CONFIRMING_ORDER,     // 주문 확인중
    PREPARING,            // 조리중
    DELIVERING,           // 배송중
    DELIVERY_COMPLETE,    // 배송완료
    ORDER_CANCELED,       // 주문취소
    PAYMENT_CANCELED;     // 결제 취소
}
