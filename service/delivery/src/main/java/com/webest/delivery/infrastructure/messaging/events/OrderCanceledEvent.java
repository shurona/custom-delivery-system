package com.webest.delivery.infrastructure.messaging.events;

import com.webest.delivery.application.dtos.DeliveryDto;
import com.webest.delivery.domain.model.DeliveryStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderCanceledEvent {

    private Long id;

}
