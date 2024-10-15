package com.webest.order.infrastructure.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class UserFallbackFactory implements FallbackFactory<UserFallback> {

    @Override
    public UserFallback create(Throwable cause) {
        log.info(cause.toString());
        return new UserFallback(cause);
    }
}
