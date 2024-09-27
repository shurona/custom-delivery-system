package com.webest.coupon.infrastructure;

import com.webest.coupon.domain.Coupon;
import com.webest.coupon.domain.CouponRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CouponJpaRepository extends JpaRepository<Coupon, Long> {

}
