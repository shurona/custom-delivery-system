package com.webest.coupon.presentation.dtos.response;

import com.webest.coupon.domain.model.DateType;
import com.webest.coupon.domain.model.DiscountType;
import java.time.LocalDateTime;

public record CouponResponseDto(
    Long couponId,
    String content,
    LocalDateTime startTime,
    LocalDateTime endTime,
    Integer duration,
    DateType dateType,
    DiscountType discountType,
    Integer discountValue,
    LocalDateTime createTime,
    Integer quantity
) {

}
