package com.webest.delivery.presentation.controller;

import com.webest.delivery.application.service.DeliveryRecordService;
import com.webest.delivery.presentation.reqeust.DeliveryRecordSearchRequest;
import com.webest.delivery.presentation.response.DeliveryRecordResponse;
import com.webest.delivery.presentation.response.DeliveryResponse;
import com.webest.web.common.CommonStaticVariable;
import com.webest.web.common.UserRole;
import com.webest.web.response.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/delivery-records")
public class DeliveryRecordController {

    private final DeliveryRecordService deliveryRecordService;


    @GetMapping("/{deliveryRecordId}")
    public CommonResponse<DeliveryRecordResponse> getDelivery(@RequestHeader(name = CommonStaticVariable.X_USER_ID) Long userId,
                                                              @RequestHeader(name = CommonStaticVariable.X_USER_ROLE) UserRole userRole,
                                                              @PathVariable(name = "deliveryRecordId") Long deliveryRecordId) {


        return CommonResponse.success(deliveryRecordService.getDeliveryRecord(userId, userRole, deliveryRecordId));
    }


    @GetMapping
    public CommonResponse<List<DeliveryRecordResponse>> getAllDeliveryRecords(@RequestHeader(name = CommonStaticVariable.X_USER_ID) Long userId,
                                                                              @RequestHeader(name = CommonStaticVariable.X_USER_ROLE) UserRole userRole) {


        return CommonResponse.success(deliveryRecordService.getAllDeliveryRecords(userId, userRole));
    }

    @GetMapping("/search")
    public CommonResponse<?> searchDeliveryRecords(@RequestHeader(name = CommonStaticVariable.X_USER_ID) Long userId,
                                                   @RequestHeader(name = CommonStaticVariable.X_USER_ROLE) UserRole userRole,
                                                   @RequestBody DeliveryRecordSearchRequest deliveryRecordSearchRequest,
                                                   @RequestParam(defaultValue = "1") int page,
                                                   @RequestParam(defaultValue = "20") int size) {

        PageRequest pageRequest = PageRequest.of(page - 1, size);

        return CommonResponse.success(deliveryRecordService.searchDeliveryRecord(userId, userRole, deliveryRecordSearchRequest.toDto(), pageRequest));
    }

}
