package com.webest.coupon.application;

import com.webest.coupon.domain.model.CouponSearchCondition;
import com.webest.coupon.presentation.dtos.request.CouponCreateRequestDto;
import com.webest.coupon.presentation.dtos.request.CouponUpdateRequestDto;
import com.webest.coupon.presentation.dtos.response.CouponResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CouponService {

    /**
     * 쿠폰 생성
     */
    public Long createCoupon(CouponCreateRequestDto requestDto);
    
    /**
     * 쿠폰 단일 조회
     */
    public CouponResponseDto findCouponById(Long id);

    /**
     * 쿠폰 목록 조회
     */
    public Page<CouponResponseDto> findCouponList(
        CouponSearchCondition condition, Pageable pageable);

    /**
     * 쿠폰 변경
     */
    public CouponResponseDto updateCouponById(CouponUpdateRequestDto requestDto, Long id);


    /**
     * 쿠폰 삭제
     */
    Long deleteCouponById(Long id);
}
