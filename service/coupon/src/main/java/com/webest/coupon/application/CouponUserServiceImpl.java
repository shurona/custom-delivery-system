package com.webest.coupon.application;

import com.webest.coupon.common.exception.CouponErrorCode;
import com.webest.coupon.common.exception.CouponException;
import com.webest.coupon.domain.dtos.CouponByUserDto;
import com.webest.coupon.domain.model.Coupon;
import com.webest.coupon.domain.model.CouponUser;
import com.webest.coupon.domain.repository.CouponQueryRepository;
import com.webest.coupon.domain.repository.CouponRepository;
import com.webest.coupon.mapper.CouponMapper;
import com.webest.coupon.presentation.dtos.response.CouponByUserResponseDto;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class CouponUserServiceImpl implements CouponUserService {

    //
    private final CouponRepository couponRepository;
    private final CouponQueryRepository couponQueryRepository;

    private final CouponMapper couponMapper;

    @Override
    public List<CouponByUserResponseDto> findCouponListByUser(Long userId, Boolean used) {

        List<CouponByUserDto> couponListByUserId =
            couponQueryRepository.findCouponListByUserId(userId, used);

        return couponMapper.couponByUserDtoToResponseDto(couponListByUserId);
    }

    @Transactional
    @Override
    public boolean issueCouponToUser(Long couponId, Long userId) {

        Coupon coupon = couponByIdAndCheck(couponId);

        // issue coupon
        LocalDateTime expiredTime = coupon.issueCoupon();

        CouponUser couponUser = CouponUser.from(userId, coupon, expiredTime);
        coupon.addUserToCoupon(couponUser);

        return true;
    }

    @Transactional
    @Override
    public boolean useCouponByUser(Long couponId, Long userId) {

        Coupon coupon = couponByIdWithCouponUser(couponId, userId);

        // 쿠폰을 보유하고 있는지 확인
        if (coupon.getCouponUserList().isEmpty()) {
            throw new CouponException(CouponErrorCode.NO_OWNED_COUPON);
        }

        coupon.getCouponUserList().get(0).useCoupon();

        return true;
    }

    /* ==========================================================================================
            private method
        ==========================================================================================*/
    private Coupon couponByIdAndCheck(Long id) {
        return couponRepository.findById(id).orElseThrow(() ->
            new CouponException(CouponErrorCode.COUPON_NOT_FOUND)
        );
    }

    private Coupon couponByIdWithCouponUser(Long couponId, Long userId) {
        return couponRepository.findCouponByCouponIdAndUserId(couponId, userId)
            .orElseThrow(() ->
                new CouponException(CouponErrorCode.NO_OWNED_COUPON)
            );
    }
}
