package com.webest.delivery.infrastructure.messaging.consumer;

import com.webest.delivery.application.service.DeliveryService;
import com.webest.delivery.infrastructure.messaging.events.OrderCanceledEvent;
import com.webest.delivery.infrastructure.messaging.events.OrderRequestedEvent;
import com.webest.delivery.infrastructure.serialization.EventSerializer;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderEventConsumer {

    private final DeliveryService deliveryService;

    @KafkaListener(topics = "order-requested", groupId = "delivery-group")
    public void handleOrderCompletedEvent(String message) {
        OrderRequestedEvent orderEvent = EventSerializer.deserialize(message, OrderRequestedEvent.class);
        deliveryService.createDelivery(orderEvent.toDeliveryDto());
    }

    @KafkaListener(topics = "order-canceled", groupId = "delivery-group")
    public void handleOrderCanceledEvent(String message) {
        OrderCanceledEvent orderEvent = EventSerializer.deserialize(message, OrderCanceledEvent.class);
        deliveryService.cancelDelivery(orderEvent.getId());
    }


}
