package com.webest.coupon.application.client;

import static com.webest.web.common.CommonStaticVariable.X_USER_ID;

import com.webest.coupon.application.client.dtos.UserResponse;
import com.webest.web.response.CommonResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "user-service")
public interface UserClient {

    @GetMapping("/api/users/myPage")
    CommonResponse<UserResponse> findUserById(
        @RequestHeader(X_USER_ID) String userId
    );
}
