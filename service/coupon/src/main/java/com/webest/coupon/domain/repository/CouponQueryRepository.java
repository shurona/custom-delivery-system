package com.webest.coupon.domain.repository;

import com.webest.coupon.domain.model.Coupon;
import com.webest.coupon.domain.model.CouponSearchCondition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CouponQueryRepository {

    Page<Coupon> findCouponListByQuery(CouponSearchCondition condition, Pageable pageable);

}
