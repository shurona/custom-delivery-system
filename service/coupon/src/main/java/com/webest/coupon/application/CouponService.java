package com.webest.coupon.application;

import com.webest.coupon.presentation.dtos.request.CouponCreateRequestDto;
import com.webest.coupon.presentation.dtos.response.CouponResponseDto;

public interface CouponService {

    public Long createCoupon(CouponCreateRequestDto requestDto);

    public CouponResponseDto findCouponById(Long id);

}
