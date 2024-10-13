package com.webest.store.store.domain.strategy;

import com.webest.store.store.domain.repository.CustomStoreRepository;
import com.webest.store.store.infrastructure.user.UserClient;
import com.webest.store.store.infrastructure.user.dto.UserResponse;
import com.webest.store.store.presentation.dto.StoreResponse;
import com.webest.web.common.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class UserStoreStrategy implements StoreStrategy {

    private final CustomStoreRepository customStoreRepository;
    private final UserClient userClient;

    @Override
    public List<StoreResponse> getDeliveryStores(String userId) {
        UserResponse user = userClient.getUser(userId).getData();
        return customStoreRepository.findStoresByAddressCode(user.addressCode())
                .stream()
                .map(StoreResponse::of)
                .toList();
    }

    @Override
    public List<StoreResponse> getTakeOutStores(String userId) {
        return List.of();
    }

    @Override
    public boolean checkUserRole(UserRole role) {
        return role == UserRole.USER || role == UserRole.OWNER;
    }
}
