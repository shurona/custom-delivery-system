package com.webest.coupon.common.value;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum CouponRedisStatus {
    OPEN(0),
    OUT_OF_STOCK(1),
    CLOSE(2);

    private final int value;

}
