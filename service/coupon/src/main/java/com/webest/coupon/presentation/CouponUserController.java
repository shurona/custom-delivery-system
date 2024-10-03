package com.webest.coupon.presentation;


import com.webest.coupon.application.CouponUserService;
import com.webest.coupon.presentation.dtos.request.CouponUsedRequestDto;
import com.webest.coupon.presentation.dtos.response.CouponByUserResponseDto;
import com.webest.web.response.CommonResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/coupons")
public class CouponUserController {

    private final CouponUserService couponUserService;

    /**
     * 유저 소유한 쿠폰 목록 조회
     */
    @GetMapping("/users/{userId}")
    public CommonResponse<List<CouponByUserResponseDto>> findCouponByUserId(
        @PathVariable("userId") Long userId,
        @RequestParam(value = "used", required = false) Boolean used
    ) {

        List<CouponByUserResponseDto> couponListByUser = couponUserService.findCouponListByUser(
            userId, used);

        return CommonResponse.success(couponListByUser);
    }

    /**
     * 쿠폰 발급
     */
    @PostMapping("/{couponId}/users/{userId}")
    public CommonResponse<Boolean> issueCoupon(
        @PathVariable("couponId") Long couponId,
        @PathVariable("userId") Long userId
    ) {
        //TODO: Header의 userId와 비교

        boolean success = couponUserService.issueCouponToUser(couponId, userId);

        return CommonResponse.success(success);
    }

    /**
     * 쿠폰 사용
     */
    @PatchMapping("/used")
    public CommonResponse<Boolean> useCoupon(
        @Validated @RequestBody CouponUsedRequestDto requestDto
    ) {
        // TODO: Header의 userId와 비교

        boolean success = couponUserService.useCouponByUser(
            requestDto.userCouponId(),
            requestDto.couponId(),
            requestDto.userId());

        return CommonResponse.success(success);
    }

}
