package com.webest.coupon.presentation;

import com.webest.coupon.application.CouponService;
import com.webest.coupon.common.exception.CouponErrorCode;
import com.webest.coupon.common.exception.CouponException;
import com.webest.coupon.domain.model.CouponSearchCondition;
import com.webest.coupon.presentation.dtos.request.CouponCreateRequestDto;
import com.webest.coupon.presentation.dtos.request.CouponUpdateRequestDto;
import com.webest.coupon.presentation.dtos.response.CouponResponseDto;
import com.webest.web.common.CommonStaticVariable;
import com.webest.web.common.UserRole;
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
import org.springframework.web.bind.annotation.RequestHeader;
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
        @RequestHeader(name = CommonStaticVariable.X_USER_ROLE) UserRole userRole,
        @Validated @RequestBody CouponCreateRequestDto requestDto
    ) {
        // 쿠폰 생성은 마스터만 가능
        if (!userRole.equals(UserRole.MASTER)) {
            throw new CouponException(CouponErrorCode.FORBIDDEN_INPUT);
        }

        Long couponId = couponService.createCoupon(requestDto);
        return CommonResponse.success(couponId);
    }

    /**
     * 쿠폰 단일 조회
     */
    @GetMapping("/{id}")
    public CommonResponse<CouponResponseDto> findCouponById(
        @RequestHeader(name = CommonStaticVariable.X_USER_ID) String userId,
        @RequestHeader(name = CommonStaticVariable.X_USER_ROLE) UserRole userRole,
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
        @RequestHeader(name = CommonStaticVariable.X_USER_ROLE) UserRole userRole,
        @PageableDefault(size = 10, page = 0) Pageable pageable,
        @ModelAttribute CouponSearchCondition condition
    ) {
        // 쿠폰 조회는 마스터만 가능
        if (!userRole.equals(UserRole.MASTER)) {
            throw new CouponException(CouponErrorCode.FORBIDDEN_INPUT);
        }

        Page<CouponResponseDto> couponList = couponService.findCouponList(condition, pageable);

        return CommonResponse.success(couponList);
    }

    /**
     * 쿠폰 정보 업데이트
     */
    @PatchMapping("/{id}")
    public CommonResponse<CouponResponseDto> updateCouponInfo(
        @RequestHeader(name = CommonStaticVariable.X_USER_ROLE) UserRole userRole,
        @RequestBody CouponUpdateRequestDto requestDto,
        @PathVariable("id") Long id
    ) {
        // 쿠폰 정보 수정은 마스터만 가능
        if (!userRole.equals(UserRole.MASTER)) {
            throw new CouponException(CouponErrorCode.FORBIDDEN_INPUT);
        }

        CouponResponseDto couponResponseDto = couponService.updateCouponById(requestDto, id);

        return CommonResponse.success(couponResponseDto);
    }

    /**
     * 쿠폰 삭제(비활성화)
     */
    @DeleteMapping("/{id}")
    public CommonResponse<Long> deleteCouponById(
        @RequestHeader(name = CommonStaticVariable.X_USER_ROLE) UserRole userRole,
        @PathVariable("id") Long id
    ) {
        // 쿠폰 정보 수정은 마스터만 가능
        if (!userRole.equals(UserRole.MASTER)) {
            throw new CouponException(CouponErrorCode.FORBIDDEN_INPUT);
        }

        Long couponId = couponService.deleteCouponById(id);

        return CommonResponse.success(couponId);
    }


}
