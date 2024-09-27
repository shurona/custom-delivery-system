package com.webest.coupon.presentation.dtos.request;

import jakarta.validation.constraints.NotNull;

public record CouponCreateRequestDto(
    @NotNull(message = "쿠폰 내용은 비어있을 수 없습니다.")
    String content,

    @NotNull(message = "기간을 비어있을 수 없습니다.")
    Integer duration
) {

}
