package com.webest.coupon.domain.service;

import com.webest.coupon.domain.dtos.CouponCheckData;
import com.webest.coupon.domain.model.Coupon;

public interface CouponDomainService {

    public Coupon checkValidCreateCoupon(CouponCheckData coupon, String content);

    public void checkValidUpdateCoupon(CouponCheckData coupon, String content);

    public void checkIssueCouponCondition(Coupon coupon);

}
