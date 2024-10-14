package com.webest.coupon.presentation.dtos.request;

public record CouponKafkaIssueDto(
    Long couponId,
    String userId
) {

    @Override
    public String toString() {
        return "CouponKafkaIssueDto{" +
            "couponId=" + couponId +
            ", userId=" + userId +
            '}';
    }
}
