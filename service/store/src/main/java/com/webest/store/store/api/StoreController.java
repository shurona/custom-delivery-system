package com.webest.store.store.api;

import com.webest.store.store.api.dto.CreateStoreRequest;
import com.webest.store.store.api.dto.StoreResponse;
import com.webest.store.store.application.StoreService;
import com.webest.web.response.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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

    // 가게 단건 조회
    @GetMapping("{id}")
    public CommonResponse<StoreResponse> getStoreById(@PathVariable("id") Long id) {
        StoreResponse response = storeService.getStoreById(id);
        return CommonResponse.success(response);
    }
}
