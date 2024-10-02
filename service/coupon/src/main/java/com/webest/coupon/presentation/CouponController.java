package com.webest.coupon.presentation;

import com.webest.coupon.application.CouponService;
import com.webest.coupon.domain.model.CouponSearchCondition;
import com.webest.coupon.presentation.dtos.request.CouponCreateRequestDto;
import com.webest.coupon.presentation.dtos.request.CouponUpdateRequestDto;
import com.webest.coupon.presentation.dtos.response.CouponResponseDto;
import com.webest.web.response.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/coupons")
public class CouponController {

    private final CouponService couponService;

    /**
     * 쿠폰 생성
     */
    @PostMapping
    public CommonResponse<Long> createCoupon(
        @Validated @RequestBody CouponCreateRequestDto requestDto
    ) {
        Long couponId = couponService.createCoupon(requestDto);
        return CommonResponse.success(couponId);
    }

    /**
     * 쿠폰 단일 조회
     */
    @GetMapping("/{id}")
    public CommonResponse<CouponResponseDto> findCouponById(
        @PathVariable("id") Long id
    ) {

        CouponResponseDto coupon = couponService.findCouponById(id);

        return CommonResponse.success(coupon);
    }

    /**
     * 쿠폰 목록 조회
     */
    @GetMapping
    public CommonResponse<Page<CouponResponseDto>> findCouponList(
        @PageableDefault(size = 10, page = 0) Pageable pageable,
        @ModelAttribute CouponSearchCondition condition
    ) {

        Page<CouponResponseDto> couponList = couponService.findCouponList(condition, pageable);

        return CommonResponse.success(couponList);
    }

    /**
     * 쿠폰 정보 업데이트
     */
    @PatchMapping("/{id}")
    public CommonResponse<CouponResponseDto> updateCouponInfo(
        @RequestBody CouponUpdateRequestDto requestDto,
        @PathVariable("id") Long id
    ) {

        CouponResponseDto couponResponseDto = couponService.updateCouponById(requestDto, id);

        return CommonResponse.success(couponResponseDto);
    }

    /**
     * 쿠폰 삭제(비활성화)
     */
    @DeleteMapping("/{id}")
    public CommonResponse<Long> deleteCouponById(
        @PathVariable("id") Long id
    ) {

        Long couponId = couponService.deleteCouponById(id);

        return CommonResponse.success(couponId);
    }


}
