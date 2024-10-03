package com.webest.coupon.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "p_coupon_user")
public class CouponUser {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long couponUserId;

    @Column(name = "user_id")
    private Long userId;

    @Column
    @ColumnDefault(value = "0")
    private boolean used = false;

    // Coupon_User에서는 BaseEntity를 받지 않음
    @Column(name = "created_at", updatable = false)
    @CreatedDate
    private LocalDateTime createdAt;

    @CreatedBy
    @Column(updatable = false)
    private Long createdBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coupon_id")
    private Coupon coupon;

    @Column(name = "expired_time", nullable = false)
    private LocalDateTime expiredTime;

    @Column(name = "used_time")
    private LocalDateTime usedTime;

    public static CouponUser from(
        Long userId, Coupon coupon, LocalDateTime expiredTime) {
        CouponUser couponUser = new CouponUser();
        couponUser.coupon = coupon;
        couponUser.userId = userId;
        couponUser.expiredTime = expiredTime;

        return couponUser;
    }

    public void useCoupon() {
        this.used = true;
    }

}
