package com.webest.coupon.presentation.dtos.request;

import jakarta.validation.constraints.NotNull;

public record CouponUsedRequestDto(
    @NotNull
    Long userCouponId,
    @NotNull
    String userId,
    @NotNull
    Long couponId
) {

}
