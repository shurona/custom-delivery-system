package com.webest.order.domain.events;

import com.webest.order.domain.model.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderCanceledEvent {

    private Long id;

}
