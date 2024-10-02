package com.webest.coupon.infrastructure;

import com.webest.coupon.domain.model.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CouponJpaRepository extends JpaRepository<Coupon, Long> {

}
