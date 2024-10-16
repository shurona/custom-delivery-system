package com.webest.coupon.presentation.dtos.response;

public record CouponOffsetResponseDto(
    String userId,
    Long couponId,
    Long offset
) {

}
