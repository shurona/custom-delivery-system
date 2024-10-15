package com.webest.coupon.presentation.dtos.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.webest.coupon.domain.model.DateType;
import com.webest.coupon.domain.model.DiscountType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.time.LocalDate;

public record CouponCreateRequestDto(
    @NotNull(message = "쿠폰 내용은 비어있을 수 없습니다.")
    String content,

    @NotNull(message = "기간을 비어있을 수 없습니다.")
    @Positive
    Integer duration,

    @NotNull
    DateType dateType,

    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd")
    LocalDate startTime,

    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd")
    LocalDate endTime,

    @NotNull
    DiscountType discountType,
    @NotNull
    @Positive
    Integer discountValue,

    @NotNull
    @Positive
    Integer maxQuantity
) {

}
