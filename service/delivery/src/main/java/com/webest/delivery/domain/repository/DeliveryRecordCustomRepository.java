package com.webest.delivery.domain.repository;

import com.webest.delivery.application.dtos.DeliveryDto;
import com.webest.delivery.application.dtos.DeliveryRecordDto;
import com.webest.delivery.application.dtos.DeliveryRecordSearchDto;
import com.webest.delivery.domain.model.Delivery;
import com.webest.delivery.domain.model.DeliveryRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DeliveryRecordCustomRepository {
    Page<DeliveryRecord> searchDeliveryRecord(DeliveryRecordSearchDto request, Pageable pageable);
}
