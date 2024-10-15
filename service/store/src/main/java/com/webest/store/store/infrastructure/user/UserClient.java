package com.webest.store.store.infrastructure.user;

import com.webest.store.store.infrastructure.user.dto.UserResponse;
import com.webest.web.response.CommonResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "user-service")
public interface UserClient {
    @GetMapping("/api/users/myPage")
    CommonResponse<UserResponse> getUser(
            @RequestHeader("X-UserId") String userId
    );
}
