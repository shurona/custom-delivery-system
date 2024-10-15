package com.webest.order.infrastructure.client.store;

import com.webest.order.infrastructure.configuration.StoreFeignClientConfig;
import com.webest.order.infrastructure.client.store.dto.StoreResponse;
import com.webest.web.response.CommonResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
        name = "store-service",
        url = "http://127.0.0.1:19093",
        configuration = StoreFeignClientConfig.class,
        fallbackFactory = StoreFallbackFactory.class
)
public interface StoreClient {

    @GetMapping("/api/v1/stores/{id}")
    CommonResponse<StoreResponse> getStoreById(@PathVariable("id") Long id);

}
