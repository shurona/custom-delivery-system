package com.webest.order.infrastructure.messaging.topic;


import lombok.Getter;

@Getter
public enum OrderTopic {

    CREATED("order-created"),
    COMPLETED("order-completed"),
    UPDATED("order-updated"),
    CANCELED("order-cancel");

    private final String topic;

    OrderTopic(String topic) {
        this.topic = topic;
    }

}
