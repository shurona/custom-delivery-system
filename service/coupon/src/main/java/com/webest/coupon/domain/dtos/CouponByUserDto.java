package com.webest.coupon.domain.dtos;

import java.time.LocalDateTime;

public record CouponByUserDto(
    Long couponId,
    String content,
    LocalDateTime expiredTime,
    boolean used
) {

}
