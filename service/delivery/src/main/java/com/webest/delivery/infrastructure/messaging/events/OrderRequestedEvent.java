package com.webest.delivery.infrastructure.messaging.events;

import com.webest.delivery.application.dtos.DeliveryDto;
import com.webest.delivery.domain.model.DeliveryStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequestedEvent {

    private Long id;

    private Long storeId;

    private Long paymentId;

    private Long couponId;

    private Long userId;

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

    public DeliveryDto toDto() {
        return DeliveryDto.create(
                this.id,
                null, // 요청때는 라이더 id 없음
                this.requestsToRider,
                DeliveryStatus.REQUEST,
                this.storeAddressCode,
                this.storeDetailAddress,
                this.arrivalAddressCode,
                this.arrivalDetailAddress,
                null // 배달료 가맹점에 설정 된 배달료
        );
    }

}
