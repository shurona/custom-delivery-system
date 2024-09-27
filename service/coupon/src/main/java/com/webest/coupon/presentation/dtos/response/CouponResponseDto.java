package com.webest.coupon.presentation.dtos.response;

import com.webest.coupon.domain.Coupon;
import java.time.LocalDateTime;

public record CouponResponseDto(
    Long couponId,
    String content,
    LocalDateTime startTime,
    LocalDateTime endTime,
    Integer duration,
    String dateType
) {

    public static CouponResponseDto from(Coupon coupon) {
        return new CouponResponseDto(
            coupon.getCouponId(),
            coupon.getContent(),
            coupon.getStartTime(),
            coupon.getEndTime(),
            coupon.getDuration(),
            coupon.getDateType()
        );
    }

}
