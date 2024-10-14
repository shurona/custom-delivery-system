package com.webest.order.infrastructure.client;

import com.webest.order.domain.service.UserService;
import com.webest.order.presentation.response.UserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(
        name = "user-service"
)
public interface UserFeignClient extends UserService {

    @GetMapping("/api/users/myPage")
    UserResponse getUser(@RequestHeader("X-UserId") String userId);

}
