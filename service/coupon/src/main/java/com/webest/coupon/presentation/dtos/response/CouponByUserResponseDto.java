package com.webest.coupon.presentation.dtos.response;

import java.time.LocalDateTime;

public record CouponByUserResponseDto(
    Long userCouponId,
    Long couponId,
    String content,
    LocalDateTime expiredTime,
    Boolean used
) {

}
