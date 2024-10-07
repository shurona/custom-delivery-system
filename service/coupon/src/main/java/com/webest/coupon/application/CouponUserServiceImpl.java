package com.webest.coupon.application;

import com.webest.coupon.common.aop.RedissonLock;
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
import java.util.Objects;
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


    @RedissonLock(value = "#couponId")
    @Transactional
    public boolean issueCouponToUser(Long couponId, Long userId) {
        Coupon coupon = couponByIdWithLockAndCheck(couponId);

        // issue coupon
        LocalDateTime expiredTime = coupon.issueCoupon();

        CouponUser couponUser = CouponUser.from(userId, coupon, expiredTime);
        coupon.addUserToCoupon(couponUser);

        return true;
    }

    @Transactional
    @Override
    public boolean useCouponByUser(Long userCouponId, Long couponId, Long userId) {

        Coupon coupon = findCouponByUserCouponId(userCouponId);

        List<CouponUser> couponUserList = coupon.getCouponUserList();
        // 쿠폰을 보유하고 있는지 확인
        if (couponUserList.isEmpty()) {
            throw new CouponException(CouponErrorCode.NOT_OWNED_COUPON);
        }

        CouponUser couponUser = couponUserList.get(0);

        // 쿠폰을 보유하고 있는지 확인
        if (!Objects.equals(couponUser.getUserId(), userId)
            || !Objects.equals(couponUser.getCoupon().getCouponId(), couponId)) {
            throw new CouponException(CouponErrorCode.NOT_OWNED_COUPON);
        }

        couponUser.useCoupon();

        return true;
    }

    /* ==========================================================================================
            private method
        ==========================================================================================*/
    private Coupon couponByIdWithLockAndCheck(Long id) {
        return couponRepository.findCouponByCouponId(id).orElseThrow(() ->
            new CouponException(CouponErrorCode.COUPON_NOT_FOUND)
        );
    }

    private Coupon couponByIdWithCouponUser(Long couponId, Long userId) {
        return couponRepository.findCouponByCouponIdAndUserId(couponId, userId)
            .orElseThrow(() ->
                new CouponException(CouponErrorCode.NOT_OWNED_COUPON)
            );
    }

    private Coupon findCouponByUserCouponId(Long userCouponId) {
        return couponRepository.findCouponByCouponUserId(userCouponId)
            .orElseThrow(() ->
                new CouponException(CouponErrorCode.NOT_OWNED_COUPON)
            );
    }
}
