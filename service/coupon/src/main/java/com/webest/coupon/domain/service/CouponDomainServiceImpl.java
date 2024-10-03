package com.webest.coupon.domain.service;

import static com.webest.coupon.common.value.CouponStaticValue.PERCENTAGE_MAX_VALUE;

import com.webest.coupon.common.exception.CouponErrorCode;
import com.webest.coupon.common.exception.CouponException;
import com.webest.coupon.domain.dtos.CouponCheckData;
import com.webest.coupon.domain.model.Coupon;
import com.webest.coupon.domain.model.DiscountType;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CouponDomainServiceImpl implements CouponDomainService {

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

    private void checkUnderZero(Integer duration, Integer quantity) {
        if (duration < 0 || quantity < 0) {
            throw new CouponException(CouponErrorCode.CHECK_UNDER_ZERO);
        }
    }


}
