package com.webest.store.store.domain.strategy;

import com.webest.store.store.domain.model.Store;
import com.webest.store.store.domain.repository.StoreRepository;
import com.webest.store.store.infrastructure.user.dto.UserResponse;
import com.webest.store.store.presentation.dto.StoreResponse;
import com.webest.web.common.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class MasterStoreStrategy implements StoreStrategy {

    private final StoreRepository storeRepository;

    @Override
    public List<StoreResponse> getDeliveryStores(String userId) {
        List<Store> stores = storeRepository.findAll();
        return stores.stream()
                .map(StoreResponse::of)
                .toList();
    }

    @Override
    public List<StoreResponse> getTakeOutStores(String userId) {
        List<Store> stores = storeRepository.findAll();
        return stores.stream()
                .map(StoreResponse::of)
                .toList();
    }

    @Override
    public boolean checkUserRole(UserRole role) {
        return role == UserRole.MASTER;
    }
}
