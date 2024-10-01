package com.webest.store.store.application;

import com.webest.store.store.api.dto.CreateStoreRequest;
import com.webest.store.store.api.dto.StoreResponse;
import com.webest.store.store.domain.Store;
import com.webest.store.store.domain.StoreRepository;
import com.webest.store.store.domain.StoreStatus;
import com.webest.store.store.exception.StoreErrorCode;
import com.webest.store.store.exception.StoreException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class StoreService {

    private final StoreRepository storeRepository;

    // 가게 생성
    // 가게 주소, 위경도, 배달 반경, 배달 팁은 생성 시 설정하지 않고 따로 업데이트
    @Transactional
    public StoreResponse saveStore(CreateStoreRequest request) {
        Store store = request.toEntity();
        storeRepository.save(store);
        return StoreResponse.of(store);
    }

    // 가게 단건 조회
    public StoreResponse getStoreById(Long id) {
        Store store = findStoreById(id);
        return StoreResponse.of(store);
    }



    // ID로 카테고리를 찾는 공통 메서드
    private Store findStoreById(Long id) {
        return storeRepository.findById(id).orElseThrow(
                () -> new StoreException(StoreErrorCode.STORE_NOT_FOUND)
        );
    }
}
