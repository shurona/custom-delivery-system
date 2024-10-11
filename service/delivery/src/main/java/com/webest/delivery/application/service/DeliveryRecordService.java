package com.webest.delivery.application.service;

import com.webest.delivery.application.dtos.DeliveryRecordDto;
import com.webest.delivery.application.dtos.DeliveryRecordSearchDto;
import com.webest.delivery.domain.exception.DeliveryException;
import com.webest.delivery.domain.exception.ErrorCode;
import com.webest.delivery.domain.model.DeliveryRecord;
import com.webest.delivery.domain.repository.DeliveryRecordRepository;
import com.webest.delivery.presentation.reqeust.DeliveryRecordSearchRequest;
import com.webest.delivery.presentation.response.DeliveryRecordResponse;
import com.webest.delivery.presentation.response.DeliveryResponse;
import com.webest.web.common.UserRole;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DeliveryRecordService {

    private final DeliveryRecordRepository deliveryRecordRepository;

    @Transactional
    public DeliveryRecordResponse createDeliveryRecord(DeliveryRecordDto request) {

        DeliveryRecord deliveryRecord = DeliveryRecord.create(
                request.deliveryId(),
                request.riderId(),
                request.orderId(),
                request.deliveryStatus(),
                request.deliveryFeeAmount()
        );

        // 배달 저장
        deliveryRecordRepository.save(deliveryRecord);

        return DeliveryRecordResponse.of(deliveryRecord);
    }


    @Transactional
    public DeliveryRecordResponse updateDeliveryRecord(Long deliveryRecordId, DeliveryRecordDto request) {

        return deliveryRecordRepository.findById(deliveryRecordId).map(deliveryRecord -> {
            deliveryRecord.update(
                    request.deliveryId(),
                    request.riderId(),
                    request.orderId(),
                    request.deliveryStatus(),
                    request.deliveryFeeAmount()
            );
            return DeliveryRecordResponse.of(deliveryRecord);

        }).orElseThrow(() -> new DeliveryException(ErrorCode.DELIVERY_RECORD_NOT_FOUND));

    }

    @Transactional
    public DeliveryRecordResponse getDeliveryRecord(Long userId, UserRole userRole, Long deliveryRecordId) {

        return DeliveryRecordResponse.of(deliveryRecordRepository.findById(deliveryRecordId)
                .orElseThrow(() -> new DeliveryException(ErrorCode.DELIVERY_RECORD_NOT_FOUND)));
    }

    @Transactional
    public List<DeliveryRecordResponse> getAllDeliveryRecords(Long userId, UserRole userRole) {

        return DeliveryRecordResponse.of(deliveryRecordRepository.findAll());
    }

    @Transactional
    public Page<DeliveryRecordResponse> searchDeliveryRecord(Long userId, UserRole userRole, DeliveryRecordSearchDto request, PageRequest pageRequest) {

        return deliveryRecordRepository.searchDeliveryRecord(request, pageRequest)
                .map(DeliveryRecordResponse::of);
    }




}
