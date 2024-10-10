package com.webest.coupon.application;


import com.webest.coupon.domain.Coupon;
import com.webest.coupon.domain.CouponRepository;
import com.webest.coupon.presentation.dtos.request.CouponCreateRequestDto;
import com.webest.coupon.presentation.dtos.response.CouponResponseDto;
import com.webest.web.exception.ApplicationException;
import com.webest.web.exception.ErrorCode;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class CouponServiceImpl implements CouponService {

    private final CouponRepository couponRepository;


    @Transactional
    public Long createCoupon(CouponCreateRequestDto requestDto) {

        Coupon coupon = Coupon.from(requestDto.content(), LocalDateTime.now(), LocalDateTime.now(),
            requestDto.duration(), "Day");

        Coupon cp = couponRepository.save(coupon);

        return cp.getCouponId();
    }

    @Override
    public CouponResponseDto findCouponById(Long id) {

        Coupon coupon = couponRepository.findById(id).orElseThrow(() ->
            new ApplicationException(ErrorCode.NOT_FOUND.getStatus(), ErrorCode.NOT_FOUND.getMessage())
        );

        return CouponResponseDto.from(coupon);
    }
}
