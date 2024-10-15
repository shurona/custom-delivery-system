package com.webest.coupon.domain.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
import java.util.List;

public record CouponSearchCondition(
    @JsonFormat(pattern = "yyyy-MM-dd")
    LocalDate startTime,
    @JsonFormat(pattern = "yyyy-MM-dd")
    LocalDate endTime,
    List<Long> ids
) {

}
