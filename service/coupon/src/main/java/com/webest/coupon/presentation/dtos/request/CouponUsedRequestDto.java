package com.webest.coupon.presentation.dtos.request;

import jakarta.validation.constraints.NotNull;

public record CouponUsedRequestDto(
    @NotNull
    Long userCouponId,
    @NotNull
    Long userId,
    @NotNull
    Long couponId
) {

}
