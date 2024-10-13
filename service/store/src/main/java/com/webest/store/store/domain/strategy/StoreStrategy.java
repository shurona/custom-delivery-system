package com.webest.store.store.domain.strategy;

import com.webest.store.store.infrastructure.user.dto.UserResponse;
import com.webest.store.store.presentation.dto.StoreResponse;
import com.webest.web.common.UserRole;

import java.util.List;

public interface StoreStrategy {
    // 배달 상점 조회
    List<StoreResponse> getDeliveryStores(String userId);

    // 포장 상점 조회
    List<StoreResponse> getTakeOutStores(String userId);

    boolean checkUserRole(UserRole role);
}
