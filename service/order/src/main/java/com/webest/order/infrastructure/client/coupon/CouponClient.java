package com.webest.order.infrastructure.client.coupon;

import com.webest.order.infrastructure.client.coupon.dto.CouponByUserResponseDto;
import com.webest.order.infrastructure.client.coupon.dto.CouponUsedRequestDto;
import com.webest.order.infrastructure.client.store.StoreFallbackFactory;
import com.webest.order.infrastructure.configuration.CouponFeignClientConfig;
import com.webest.order.infrastructure.configuration.StoreFeignClientConfig;
import com.webest.web.common.CommonStaticVariable;
import com.webest.web.common.UserRole;
import com.webest.web.response.CommonResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(
        name = "coupon-service",
        configuration = CouponFeignClientConfig.class,
        fallbackFactory = CouponFallbackFactory.class
)
public interface CouponClient {

    @GetMapping("/api/v1/coupons/users/{userId}")
    CommonResponse<List<CouponByUserResponseDto>> findCouponByUserId(
            @PathVariable("userId") String userId,
            @RequestHeader(name = CommonStaticVariable.X_USER_ID) String xUserId,
            @RequestHeader(name = CommonStaticVariable.X_USER_ROLE) UserRole userRole,
            @RequestParam(value = "used", required = false) Boolean used
    );

    @PatchMapping("/api/v1/coupons/used")
    CommonResponse<Boolean> useCoupon(
            @RequestHeader(name = CommonStaticVariable.X_USER_ID) String xUserId,
            @RequestHeader(name = CommonStaticVariable.X_USER_ROLE) UserRole userRole,
            @Validated @RequestBody CouponUsedRequestDto requestDto
    );

}
