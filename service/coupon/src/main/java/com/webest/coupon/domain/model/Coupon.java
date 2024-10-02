package com.webest.coupon.domain.model;


import com.webest.app.jpa.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLRestriction;

@SQLRestriction(BaseEntity.DEFAULT_CONDITION)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "p_coupon")
public class Coupon extends BaseEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long couponId;

    @Column
    private String content;

    @Column
    private Integer duration;

    @Enumerated(EnumType.STRING)
    @Column(name = "date_type")
    private DateType dateType;

    @Column(name = "start_time")
    private LocalDateTime startTime;

    @Column(name = "end_time")
    private LocalDateTime endTime;

    @Enumerated(EnumType.STRING)
    @Column(name = "discount_type")
    private DiscountType discountType;

    @Column(name = "discount_value")
    private Integer discountValue;

    @Column
    private Integer quantity;

    public static Coupon createCoupon(String content, LocalDateTime startTime,
        LocalDateTime endTime,
        Integer duration, DateType dateType, DiscountType type, Integer value, Integer quantity) {
        Coupon coupon = new Coupon();
        coupon.content = content;
        coupon.startTime = startTime;
        coupon.endTime = endTime;
        coupon.duration = duration;
        coupon.dateType = dateType;
        coupon.discountType = type;
        coupon.discountValue = value;
        coupon.quantity = quantity;

        return coupon;
    }

    public void updateCoupon(
        String content,
        Integer duration,
        DateType dateType,
        LocalDateTime startTime,
        LocalDateTime endTime,
        Integer quantity
    ) {
        this.content = content;
        this.startTime = startTime;
        this.endTime = endTime;
        this.duration = duration;
        this.dateType = dateType;
        this.quantity = quantity;
    }

    public void disableCoupon() {
        this.isDeleted = true;
    }

}
