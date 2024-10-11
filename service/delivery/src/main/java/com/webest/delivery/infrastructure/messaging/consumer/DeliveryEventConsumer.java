package com.webest.delivery.infrastructure.messaging.consumer;

import com.webest.delivery.application.service.DeliveryRecordService;
import com.webest.delivery.application.service.DeliveryService;
import com.webest.delivery.domain.events.DeliveryCompletedEvent;
import com.webest.delivery.domain.events.DeliveryCreatedEvent;
import com.webest.delivery.domain.events.DeliveryDepartedEvent;
import com.webest.delivery.domain.events.DeliveryDispatchedEvent;
import com.webest.delivery.infrastructure.messaging.events.OrderCanceledEvent;
import com.webest.delivery.infrastructure.messaging.events.OrderRequestedEvent;
import com.webest.delivery.infrastructure.serialization.EventSerializer;
import com.webest.delivery.presentation.reqeust.DeliveryCreateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeliveryEventConsumer {

    private final DeliveryRecordService deliveryRecordService;

    // 요청시 DeliveryRecord 저장
    @KafkaListener(topics = "delivery-created", groupId = "delivery-record-group")
    public void handleDeliveryCreatedEvent(String message) {
        DeliveryCreatedEvent deliveryCreatedEvent = EventSerializer.deserialize(message, DeliveryCreatedEvent.class);
        deliveryRecordService.createDeliveryRecord(DeliveryCreatedEvent.toDeliveryRecordDto(deliveryCreatedEvent));
    }

    // 배차시 DeliveryRecord 저장
    @KafkaListener(topics = "delivery-dispatched", groupId = "delivery-record-group")
    public void handleDeliveryDispatchedEvent(String message) {
        DeliveryDispatchedEvent deliveryDispatchedEvent = EventSerializer.deserialize(message, DeliveryDispatchedEvent.class);
        deliveryRecordService.createDeliveryRecord(DeliveryDispatchedEvent.toDeliveryRecordDto(deliveryDispatchedEvent));
    }

    // 출발시 DeliveryRecord 저장
    @KafkaListener(topics = "delivery-departed", groupId = "delivery-record-group")
    public void handleDeliveryDepartedEvent(String message) {
        DeliveryDepartedEvent deliveryDepartedEvent = EventSerializer.deserialize(message, DeliveryDepartedEvent.class);
        deliveryRecordService.createDeliveryRecord(DeliveryDepartedEvent.toDeliveryRecordDto(deliveryDepartedEvent));
    }

    // 완료시 DeliveryRecord 저장
    @KafkaListener(topics = "delivery-completed", groupId = "delivery-record-group")
    public void handleDeliveryCompletedEvent(String message) {
        DeliveryCompletedEvent deliveryCompletedEvent = EventSerializer.deserialize(message, DeliveryCompletedEvent.class);
        deliveryRecordService.createDeliveryRecord(DeliveryCompletedEvent.toDeliveryRecordDto(deliveryCompletedEvent));
    }


}
