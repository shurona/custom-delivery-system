package com.webest.order.infrastructure.client.store;

import com.webest.order.infrastructure.client.user.UserFallback;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class StoreFallbackFactory implements FallbackFactory<StoreFallback> {

    @Override
    public StoreFallback create(Throwable cause) {
        log.info(cause.toString());
        return new StoreFallback(cause);
    }
}
