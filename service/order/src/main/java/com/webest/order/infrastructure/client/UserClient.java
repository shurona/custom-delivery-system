package com.webest.order.infrastructure.client;

import com.webest.order.domain.service.UserService;
import com.webest.order.infrastructure.configuration.UserFeignClientConfig;
import com.webest.order.presentation.response.UserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(
        name = "user-service",
        url = "http://127.0.0.1:19092",
        configuration = UserFeignClientConfig.class,
        fallbackFactory = UserFallbackFactory.class
)
public interface UserClient extends UserService {

    @GetMapping("/api/users/myPage")
    UserResponse getUser(@RequestHeader("X-UserId") String userId);

}
