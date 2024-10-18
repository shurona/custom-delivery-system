package com.webest.order.domain.service;

import com.webest.order.infrastructure.client.coupon.CouponClient;
import com.webest.order.infrastructure.client.coupon.dto.CouponByUserResponseDto;
import com.webest.order.infrastructure.client.store.StoreClient;
import com.webest.web.common.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CouponService {

    private final CouponClient couponClient;

    public List<CouponByUserResponseDto> findCouponByUserId(String currentUserId, String userId, UserRole userRole, Boolean used) {
        return null;
    }


}
