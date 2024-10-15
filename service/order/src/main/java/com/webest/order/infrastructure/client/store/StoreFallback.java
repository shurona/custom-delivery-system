package com.webest.order.infrastructure.client.store;

import com.webest.order.infrastructure.client.store.dto.StoreResponse;
import com.webest.web.response.CommonResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class StoreFallback implements StoreClient {

    private final Throwable cause;

    public StoreFallback(Throwable cause) {
        this.cause = cause;
    }


    @Override
    public CommonResponse<StoreResponse> getStoreById(Long id) {
        return null;
    }
}
