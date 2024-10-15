package com.webest.order.domain.service;

import com.webest.order.infrastructure.client.store.StoreClient;
import com.webest.order.infrastructure.client.store.dto.StoreResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StoreService {

    private final StoreClient storeClient;

    public StoreResponse getStore(Long storeId) {
        return storeClient.getStoreById(storeId).getData();
    }


}
