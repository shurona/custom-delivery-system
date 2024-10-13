package com.webest.store.store.presentation;

import com.webest.store.store.application.context.StoreStrategyContext;
import com.webest.store.store.presentation.dto.*;
import com.webest.store.store.application.StoreService;
import com.webest.web.common.UserRole;
import com.webest.web.response.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.webest.web.common.CommonStaticVariable.X_USER_ID;
import static com.webest.web.common.CommonStaticVariable.X_USER_ROLE;

@RestController
@RequestMapping("/api/v1/stores")
@RequiredArgsConstructor
public class StoreController {
    private final StoreService storeService;
    private final StoreStrategyContext storeStrategyContext;

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
//    @GetMapping("/users")
//    public CommonResponse<List<StoreResponse>> getStoresByAddressCode(@PathVariable("addressCode") Long addressCode) {
//        List<StoreResponse> responses = storeService.getStoresByUser(addressCode);
//        return CommonResponse.success(responses);
//    }

    // 가게 단건 조회
    @GetMapping("/{id}")
    public CommonResponse<StoreResponse> getStoreById(@PathVariable("id") Long id) {
        StoreResponse response = storeService.getStoreById(id);
        return CommonResponse.success(response);
    }

    // 가게 전체 조회 (MASTER 권한)
    @GetMapping
    public CommonResponse<List<StoreResponse>> getAllStores(
            @RequestHeader("X-UserId") String userId,
            @RequestHeader("X-Role") String role
    ) {
        // String을 UserRole로 변환
        UserRole userRole = UserRole.valueOf(role);
        List<StoreResponse> responses = storeStrategyContext.getAllStores(userId, userRole);
        return CommonResponse.success(responses);
    }

    @GetMapping("/user")
    public CommonResponse<List<StoreResponse>> getStoresByUserAddressCode(
            @RequestHeader("X-UserId") String userId
    ) {
        List<StoreResponse> responses = storeService.getStoresByUserAddressCode(userId);
        return CommonResponse.success(responses);
    }

    @GetMapping("/coordinates")
    public CommonResponse<List<StoreResponse>> getStoreByCoordinates(@RequestBody Coordinates coordinates) {
        List<StoreResponse> responses = storeService.getStoresByCoordinates(coordinates);
        return CommonResponse.success(responses);
    }

    // 가게 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStore(@PathVariable("id") Long id) {
        storeService.deleteStore(id);
        return ResponseEntity.noContent().build();
    }


}
