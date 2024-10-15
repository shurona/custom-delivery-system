package com.webest.order.infrastructure.client;

import com.webest.order.presentation.response.UserResponse;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UserFallback implements UserClient{


    private final Throwable cause;

    public UserFallback(Throwable cause) {
        this.cause = cause;
    }


    @Override
    public UserResponse getUser(String userId) {
        if (cause instanceof FeignException.NotFound) {
            log.error("Not found error");
        }
        log.error("Failed to get user {}", userId);
        return new UserResponse(-1L, "", "", "", 1L, "");
    }
}
