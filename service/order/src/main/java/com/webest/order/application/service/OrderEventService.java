package com.webest.order.application.service;

import com.webest.order.infrastructure.serialization.EventSerializer;
import com.webest.order.infrastructure.messaging.topic.OrderTopic;
import com.webest.order.domain.events.OrderCanceledEvent;
import com.webest.order.domain.events.OrderCompletedEvent;
import com.webest.order.domain.events.OrderCreatedEvent;
import com.webest.order.domain.events.OrderUpdatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderEventService {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    // 주문 생성시 발생하는 이벤트
    public void publishOrderCreatedEvent(OrderCreatedEvent event) {
        kafkaTemplate.send(OrderTopic.CREATED.getTopic(), EventSerializer.serialize(event));
    }

    // 주문 완료시 발생하는 이벤트 (결제 완료까지 되었을 때)
    public void publishOrderCompletedEvent(OrderCompletedEvent event) {
        kafkaTemplate.send(OrderTopic.COMPLETED.getTopic(), EventSerializer.serialize(event));
    }

    // 주문 수정시 발생하는 이벤트
    public void publishOrderUpdatedEvent(OrderUpdatedEvent event) {
        kafkaTemplate.send(OrderTopic.UPDATED.getTopic(), EventSerializer.serialize(event));
    }

    // 주문 취소시 발생하는 이벤트
    public void publishOrderCanceledEvent(OrderCanceledEvent event) {
        kafkaTemplate.send(OrderTopic.CANCELED.getTopic(), EventSerializer.serialize(event));
    }


}
