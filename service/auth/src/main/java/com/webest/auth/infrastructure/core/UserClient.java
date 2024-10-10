package com.webest.auth.infrastructure.core;

import com.webest.auth.presentation.dto.request.UserJoinRequest;
import com.webest.auth.presentation.dto.response.JoinResponse;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "user-service")
public interface UserClient {
    @PostMapping("/api/users/signUp")
    JoinResponse createUser(@RequestBody @Valid UserJoinRequest request);
}