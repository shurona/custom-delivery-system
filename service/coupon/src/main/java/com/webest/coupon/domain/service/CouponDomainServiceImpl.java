package com.webest.coupon.domain.service;

import static com.webest.coupon.common.value.CouponStaticValue.COUPON_REDIS_IS_OUT_OF_STOCK;
import static com.webest.coupon.common.value.CouponStaticValue.PERCENTAGE_MAX_VALUE;

import com.webest.coupon.common.exception.CouponErrorCode;
import com.webest.coupon.common.exception.CouponException;
import com.webest.coupon.domain.dtos.CouponCheckData;
import com.webest.coupon.domain.model.Coupon;
import com.webest.coupon.domain.model.DiscountType;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class CouponDomainServiceImpl implements CouponDomainService {

    private final CouponRedisService couponRedisService;

    /**
     * 쿠폰 생성 시 검증
     */
    @Override
    public Coupon checkValidCreateCoupon(CouponCheckData coupon, String content) {

        checkStartEndDate(coupon.startTime(), coupon.endTime());
        checkMaxPercentageDiscount(coupon.discountType(), coupon.discountValue());
        checkUnderZero(coupon.duration(), coupon.maxQuantity());

        return Coupon.createCoupon(
            content,
            coupon.startTime(),
            coupon.endTime(),
            coupon.duration(),
            coupon.dateType(),
            coupon.discountType(),
            coupon.discountValue(),
            coupon.maxQuantity()
        );
    }

    /**
     * 업데이트 할 때는 검증만 해준다.
     */
    @Override
    public void checkValidUpdateCoupon(CouponCheckData coupon, String contents) {

        checkStartEndDate(coupon.startTime(), coupon.endTime());
        checkUnderZero(coupon.duration(), coupon.maxQuantity());

    }

    @Override
    public void checkIssueCouponCondition(Coupon coupon) {
        /**
         * 쿠폰을 발급 하면서 조건 처리한다.
         */
        // 현재 발급 가능 시간인지 확인한다.
        if (LocalDateTime.now().isBefore(coupon.getStartTime())
            || LocalDateTime.now().isAfter(coupon.getEndTime())) {
            throw new CouponException(CouponErrorCode.COUPON_NOT_ISSUE_PERIOD);
        }

        // 현재 발급 가능한 상태인지 확인한다.
        if (coupon.getIssuedQuantity() >= coupon.getMaxQuantity()) {
            couponRedisService.changeCouponOpenStatus(coupon.getCouponId(),
                COUPON_REDIS_IS_OUT_OF_STOCK, 24L);
            throw new CouponException(CouponErrorCode.COUPON_OUT_OF_STOCK);
        }
    }

    /* =============================================================================
            Coupon 확인 Private Method
         =============================================================================*/
    // 시작 날짜가 끝 날짜 전인지 확인
    private void checkStartEndDate(LocalDateTime start, LocalDateTime end) {
        if (start.isAfter(end)) {
            throw new CouponException(CouponErrorCode.STARTTIME_AFTER_ENDTIME);
        }
    }

    // 비율 할인인 경우 90을 넘기면 안된다.
    private void checkMaxPercentageDiscount(DiscountType type, Integer value) {
        if (DiscountType.PERCENTAGE.equals(type) && value > PERCENTAGE_MAX_VALUE) {
            throw new CouponException(CouponErrorCode.COUPON_PERCENTAGE_OVER_90);
        }
    }

    private void checkUnderZero(Integer duration, Integer maxQuantity) {
        if (duration < 0 || maxQuantity < 0) {
            throw new CouponException(CouponErrorCode.CHECK_UNDER_ZERO);
        }
    }


}
