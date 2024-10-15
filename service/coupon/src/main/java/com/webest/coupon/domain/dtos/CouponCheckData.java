package com.webest.coupon.domain.dtos;

import com.webest.coupon.domain.model.DateType;
import com.webest.coupon.domain.model.DiscountType;
import java.time.LocalDateTime;

public record CouponCheckData(
    Integer duration,
    DateType dateType,
    LocalDateTime startTime,
    LocalDateTime endTime,
    DiscountType discountType,
    Integer discountValue,
    Integer maxQuantity
) {


}
