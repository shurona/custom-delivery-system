package com.webest.coupon.domain;


import com.webest.app.jpa.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    @Column(name = "start_time")
    private LocalDateTime startTime;

    @Column(name = "end_time")
    private LocalDateTime endTime;

    @Column
    private Integer duration;

    @Column(name = "date_type")
    private String dateType;


    public static Coupon from(String content, LocalDateTime startTime, LocalDateTime endTime,
        Integer duration, String dateType) {
        Coupon coupon = new Coupon();
        coupon.content = content;
        coupon.startTime = startTime;
        coupon.endTime = endTime;
        coupon.duration = duration;
        coupon.dateType = dateType;
        coupon.isDeleted = false;

        return coupon;
    }

}
