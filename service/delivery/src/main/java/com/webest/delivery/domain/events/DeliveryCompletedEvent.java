package com.webest.delivery.domain.events;

import com.webest.delivery.application.dtos.DeliveryRecordDto;
import com.webest.delivery.domain.model.DeliveryStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryCompletedEvent {

    private Long id;

    private Long orderId; // 주문 id

    private String riderId; // 라이더 id

    private String requestsToRider;

    private DeliveryStatus deliveryStatus; // 배달 상태

    private Long storeAddressCode; // 가게 주소코드

    private String storeDetailAddress; // 가게 상세주소

    private Long arrivalAddressCode; // 도착 주소코드

    private String arrivalDetailAddress; // 도착 상세주소

    private Double deliveryFeeAmount; // 배달료

    public static DeliveryRecordDto toDeliveryRecordDto(DeliveryCompletedEvent deliveryCompletedEvent) {
        return DeliveryRecordDto.create(
                deliveryCompletedEvent.getId(),
                deliveryCompletedEvent.getRiderId(),
                deliveryCompletedEvent.getOrderId(),
                deliveryCompletedEvent.getDeliveryStatus(),
                deliveryCompletedEvent.getDeliveryFeeAmount()
        );
    }
}
