package com.webest.coupon.presentation.dtos.response;

public record CouponOffsetResponseDto(
    Long userId,
    Long couponId,
    Long offset
) {

}
