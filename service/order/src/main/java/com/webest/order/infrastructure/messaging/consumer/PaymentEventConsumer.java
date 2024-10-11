package com.webest.order.infrastructure.messaging.consumer;


import com.webest.order.application.service.OrderService;
import com.webest.order.infrastructure.messaging.events.PaymentCompletedEvent;
import com.webest.order.infrastructure.messaging.topic.OrderTopic;
import com.webest.order.infrastructure.serialization.EventSerializer;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PaymentEventConsumer {

    private final OrderService orderService;

    @KafkaListener(topics = "payment-completed", groupId = "order-group")
    public void handlePaymentCompletedEvent(String message) {
        PaymentCompletedEvent paymentEvent = EventSerializer.deserialize(message, PaymentCompletedEvent.class);
        orderService.paymentCompleteOrder(paymentEvent.getOrderId());
    }

}
