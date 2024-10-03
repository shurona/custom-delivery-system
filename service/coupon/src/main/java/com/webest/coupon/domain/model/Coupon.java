package com.webest.coupon.domain.model;


import com.webest.app.jpa.BaseEntity;
import com.webest.coupon.common.exception.CouponErrorCode;
import com.webest.coupon.common.exception.CouponException;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.SQLRestriction;

@Slf4j
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

    // 이벤트 시간
    @Column(name = "start_time", nullable = false)
    private LocalDateTime startTime;

    // 이벤트 종료 시간
    @Column(name = "end_time", nullable = false)
    private LocalDateTime endTime;

    @Enumerated(EnumType.STRING)
    @Column(name = "discount_type")
    private DiscountType discountType;

    @Column(name = "discount_value")
    private Integer discountValue;

    @Column(name = "max_quantity")
    private Integer maxQuantity;

    @ColumnDefault(value = "0")
    @Column(name = "issued_quantity")
    private Integer issuedQuantity = 0;

    @OneToMany(mappedBy = "coupon", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<CouponUser> couponUserList = new ArrayList<>();

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
        coupon.maxQuantity = quantity;

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
        this.maxQuantity = quantity;
    }

    public void disableCoupon() {
        this.isDeleted = true;
    }

    /*
     * User coupon 처리
     */
    public void addUserToCoupon(CouponUser couponUser) {
        this.couponUserList.add(couponUser);
    }

    /**
     * 쿠폰을 발급 하면서 조건 처리한다.
     */
    public LocalDateTime issueCoupon() {

        // 현재 발급 가능 시간인지 확인한다.
        if (LocalDateTime.now().isBefore(this.startTime)
            || LocalDateTime.now().isAfter(this.endTime)) {
            throw new CouponException(CouponErrorCode.COUPON_NOT_ISSUE_PERIOD);
        }

        // 현재 발급 가능한 상태인지 확인한다.
        if (this.issuedQuantity >= this.maxQuantity) {
            throw new CouponException(CouponErrorCode.COUPON_OUT_OF_STOCK);
        }

        //TODO: DB 정합성 문제 해결 해서 quantity를 안전하게 처리할 수 있도록 한다.
        this.issuedQuantity += 1;

        // 만료 날짜 계산 후 넘겨준다.
        return switch (this.dateType) {
            case DAY -> LocalDateTime.now().plusDays(this.duration);
            case MONTH -> LocalDateTime.now().plusMonths(this.duration);
            case HOUR -> LocalDateTime.now().plusHours(this.duration);
            default -> {
                log.error("문제있는 Enum : {}", this.dateType);
                throw new CouponException(CouponErrorCode.INTERNAL_SERVER_ERROR);
            }
        };
    }
}
