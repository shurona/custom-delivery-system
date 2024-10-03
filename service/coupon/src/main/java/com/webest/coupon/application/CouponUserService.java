package com.webest.coupon.application;

import com.webest.coupon.presentation.dtos.response.CouponByUserResponseDto;
import java.util.List;

public interface CouponUserService {

    public List<CouponByUserResponseDto> findCouponListByUser(Long userId, Boolean used);

    public boolean issueCouponToUser(Long couponId, Long userId);

    public boolean useCouponByUser(Long userCouponId, Long couponId, Long userId);

}
