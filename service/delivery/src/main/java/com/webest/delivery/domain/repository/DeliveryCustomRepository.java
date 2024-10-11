package com.webest.delivery.domain.repository;

import com.webest.delivery.application.dtos.DeliveryDto;
import com.webest.delivery.domain.model.Delivery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DeliveryCustomRepository {
    Page<Delivery> searchDelivery(DeliveryDto request, Pageable pageable);
}
