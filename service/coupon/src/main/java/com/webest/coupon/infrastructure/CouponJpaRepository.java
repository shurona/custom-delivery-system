package com.webest.coupon.infrastructure;

import com.webest.coupon.domain.model.Coupon;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CouponJpaRepository extends JpaRepository<Coupon, Long> {

    @Query("select coupon from Coupon as coupon left join fetch coupon.couponUserList as cu"
        + " where coupon.id = :couponId and cu.userId = :userId and cu.used = false"
        + " order by cu.createdAt")
    Optional<Coupon> findCouponByCouponIdAndUserId(Long couponId, Long userId);


}
