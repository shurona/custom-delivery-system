package com.webest.coupon.domain.service;

public interface CouponRedisService {

    // queue 데이터를 넣는다.
    public void addCouponToQueue(Long couponId, String userId);

    // 쿠폰 완료 후 queue 데이터를 삭제한다.
    public void popCouponFromQueue(Long couponId, String userId);

    // 남은 순서를 확인한다.
    public Long checkCurrentOffset(Long couponId, String userId);

    public void changeCouponOpenStatus(Long couponId, Integer wishStatus, long durationHour);

    public Integer checkCouponStatus(Long couponId);
}
