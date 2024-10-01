package com.webest.store.store.api;

import com.webest.store.store.api.dto.CreateStoreRequest;
import com.webest.store.store.api.dto.StoreResponse;
import com.webest.store.store.api.dto.UpdateStoreAddressRequest;
import com.webest.store.store.application.StoreService;
import com.webest.web.response.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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

    // 가게 단건 조회
    @GetMapping("{id}")
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


}
