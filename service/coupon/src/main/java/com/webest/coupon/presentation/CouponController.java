package com.webest.coupon.presentation;

import com.webest.coupon.application.CouponService;
import com.webest.coupon.presentation.dtos.request.CouponCreateRequestDto;
import com.webest.coupon.presentation.dtos.response.CouponResponseDto;
import com.webest.web.response.CommonResponse;
import java.util.Optional;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
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

    @PostMapping
    public ResponseEntity<Long> createCoupon(
        @Validated @RequestBody CouponCreateRequestDto requestDto
    ) {
        Long couponId = couponService.createCoupon(requestDto);
        return new ResponseEntity<>(couponId, HttpStatus.OK);
    }

    @GetMapping("{id}")
    public CommonResponse<CouponResponseDto> findCouponById(
        @PathVariable("id") Long id
    ) {

        CouponResponseDto coupon = couponService.findCouponById(id);

        return CommonResponse.success(coupon);
    }


}
