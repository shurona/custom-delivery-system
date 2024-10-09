package com.webest.store.store.presentation;

import com.webest.store.store.presentation.dto.CreateStoreRequest;
import com.webest.store.store.presentation.dto.DeliveryAreaRequest;
import com.webest.store.store.presentation.dto.StoreResponse;
import com.webest.store.store.presentation.dto.UpdateStoreAddressRequest;
import com.webest.store.store.application.StoreService;
import com.webest.web.response.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/stores")
@RequiredArgsConstructor
public class StoreController {
    private final StoreService storeService;

    // 가게 생성
    @PostMapping
    public CommonResponse<StoreResponse> saveStore(@RequestBody CreateStoreRequest request) {
        StoreResponse response = storeService.saveStore(request);
        return CommonResponse.success(response);
    }

    // 가게 주소 등록
    @PutMapping("/address")
    public CommonResponse<StoreResponse> updateStoreAddress(@RequestBody UpdateStoreAddressRequest request) {
        StoreResponse response = storeService.updateStoreAddress(request);
        return CommonResponse.success(response);
    }

    // 배달 범위 등록 (법정동)
    @PutMapping("/delivery-area/{id}")
    public CommonResponse<StoreResponse> registerDeliveryArea(
            @PathVariable("id") Long storeId,
            @RequestBody DeliveryAreaRequest requestDto
    ) {
        StoreResponse response = storeService.updateDeliveryArea(storeId, requestDto);
        return CommonResponse.success(response);
    }

    // 법정동으로 배달 가능 상점 검색
    @GetMapping("/users/{addressCode}")
    public CommonResponse<List<StoreResponse>> getStoresByAddressCode(@PathVariable("addressCode") Long addressCode) {
        List<StoreResponse> responses = storeService.getStoresByUser(addressCode);
        return CommonResponse.success(responses);
    }

    // 가게 단건 조회
    @GetMapping("/{id}")
    public CommonResponse<StoreResponse> getStoreById(@PathVariable("id") Long id) {
        StoreResponse response = storeService.getStoreById(id);
        return CommonResponse.success(response);
    }

    // 가게 전체 조회 (MASTER 권한)
    @GetMapping
    public CommonResponse<Page<StoreResponse>> getAllStores(@PageableDefault() Pageable pageable) {
        Page<StoreResponse> responses = storeService.getAllStores(pageable);
        return CommonResponse.success(responses);
    }

    // 가게 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStore(@PathVariable("id") Long id) {
        storeService.deleteStore(id);
        return ResponseEntity.noContent().build();
    }


}
