package com.webest.delivery.infrastructure.messaging.events;

public enum OrderStatus {
    REQUEST_ORDER,        // 주문 요청 (만약 결제에서 문제가 생겼을 시 주문취소 결제가 완료되었을 경우 주문 확인중으로 넘어감)
    CONFIRMING_ORDER,     // 주문 확인중
    PREPARING,            // 조리중
    DELIVERING,           // 배송중
    COMPLETE,             // 완료
    ORDER_CANCELED       // 주문취소
}
