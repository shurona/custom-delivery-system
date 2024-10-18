package com.webest.order.infrastructure.client.coupon;

import com.webest.order.infrastructure.client.coupon.dto.CouponByUserResponseDto;
import com.webest.order.infrastructure.client.coupon.dto.CouponUsedRequestDto;
import com.webest.web.common.UserRole;
import com.webest.web.response.CommonResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class CouponFallback implements CouponClient{

    private final Throwable cause;

    public CouponFallback(Throwable cause) {
        this.cause = cause;
    }


    @Override
    public CommonResponse<List<CouponByUserResponseDto>> findCouponByUserId(String userId, String xUserId, UserRole userRole, Boolean used) {
        return null;
    }

    @Override
    public CommonResponse<Boolean> useCoupon(String xUserId, UserRole userRole, CouponUsedRequestDto requestDto) {
        return null;
    }
}
