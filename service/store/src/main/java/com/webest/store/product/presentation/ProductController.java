package com.webest.store.product.presentation;

import com.webest.store.product.presentation.dto.CreateProductRequest;
import com.webest.store.product.presentation.dto.ProductResponse;
import com.webest.store.product.application.ProductService;
import com.webest.store.product.presentation.dto.UpdateProductDetailsRequest;
import com.webest.store.product.presentation.dto.UpdateProductStatusRequest;
import com.webest.web.common.UserRole;
import com.webest.web.response.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/products")
public class ProductController {

    private final ProductService productService;

    // 상품 생성
    @PostMapping
    public CommonResponse<ProductResponse> saveProduct(
            @RequestBody CreateProductRequest request,
            @RequestHeader("X-UserId") String userId,
            @RequestHeader("X-Role") String role
    ) {
        UserRole userRole = UserRole.valueOf(role);
        ProductResponse response = productService.saveProduct(request, userId, userRole);
        return CommonResponse.success(response);
    }

    // 상품 단건 조회
    @GetMapping("/{id}")
    public CommonResponse<ProductResponse> getProductById(@PathVariable("id") Long id) {
        ProductResponse response = productService.getProductById(id);
        return CommonResponse.success(response);
    }

    // 상품 전체 조회
    @GetMapping
    public CommonResponse<Page<ProductResponse>> getAllProducts(@PageableDefault() Pageable pageable) {
        Page<ProductResponse> responses = productService.getAllProducts(pageable);
        return CommonResponse.success(responses);
    }

    // 상품 상점별 조회
    @GetMapping("/stores/{storeId}")
    public CommonResponse<Page<ProductResponse>> getProductsByStore(@PathVariable("storeId") Long storeId, @PageableDefault() Pageable pageable) {
        Page<ProductResponse> responses = productService.getProductsByStore(storeId, pageable);
        return CommonResponse.success(responses);
    }

    // 상품 status 변경
    @PutMapping("/status")
    public CommonResponse<ProductResponse> updateStatus(
            @RequestBody UpdateProductStatusRequest request,
            @RequestHeader("X-UserId") String userId,
            @RequestHeader("X-Role") String role
    ) {
        UserRole userRole = UserRole.valueOf(role);
        ProductResponse response = productService.updateStatus(request, userId, userRole);
        return CommonResponse.success(response);
    }

    // 상품 이름, 가격, 설명 변경
    @PutMapping("/details")
    public CommonResponse<ProductResponse> updateDetails(
            @RequestBody UpdateProductDetailsRequest request,
            @RequestHeader("X-UserId") String userId,
            @RequestHeader("X-Role") String role
    ) {
        UserRole userRole = UserRole.valueOf(role);
        ProductResponse response = productService.updateDetails(request, userId, userRole);
        return CommonResponse.success(response);
    }

    // 상품 삭제
    @DeleteMapping("/{id}")
    public CommonResponse<Long> deleteProduct(
            @PathVariable("id") Long id,
            @RequestHeader("X-UserId") String userId,
            @RequestHeader("X-Role") String role
    ) {
        UserRole userRole = UserRole.valueOf(role);
        productService.deleteProduct(id, userId, userRole);
        return CommonResponse.success(id);
    }
}
