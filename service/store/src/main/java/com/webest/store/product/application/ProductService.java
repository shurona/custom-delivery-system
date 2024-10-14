package com.webest.store.product.application;

import com.webest.store.product.presentation.dto.CreateProductRequest;
import com.webest.store.product.presentation.dto.ProductResponse;
import com.webest.store.product.presentation.dto.UpdateProductDetailsRequest;
import com.webest.store.product.presentation.dto.UpdateProductStatusRequest;
import com.webest.store.product.domain.Product;
import com.webest.store.product.domain.ProductRepository;
import com.webest.store.product.domain.ProductStatus;
import com.webest.store.product.exception.ProductErrorCode;
import com.webest.store.product.exception.ProductException;
import com.webest.store.store.domain.model.Store;
import com.webest.store.store.domain.repository.StoreRepository;
import com.webest.store.store.exception.StoreErrorCode;
import com.webest.store.store.exception.StoreException;
import com.webest.web.common.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductService {
    private final ProductRepository productRepository;
    private final StoreRepository storeRepository;

    // 상품 생성
    @Transactional
    public ProductResponse saveProduct(CreateProductRequest request, String userId, UserRole role) {
        Store store = findStoreById(request.storeId());

        // 권한 검증
        validatePermission(store, userId, role);

        Product product = Product.of(store, request.name(), request.price(), request.description());
        product.updateStatus(ProductStatus.AVAILABLE);
        productRepository.save(product);
        return ProductResponse.of(product);
    }

    // 상품 단건 조회 (ALL)
    public ProductResponse getProductById(Long id) {
        Product product = findProductById(id);
        return ProductResponse.of(product);
    }

    // 상품 전체 조회 (MASTER)
    public Page<ProductResponse> getAllProducts(Pageable pageable) {
        Page<Product> products = productRepository.findAll(pageable);
        return products.map(ProductResponse::of);
    }

    // 상점별 상품 조회 (ALL)
    public Page<ProductResponse> getProductsByStore(Long storeId, Pageable pageable) {
        Page<Product> products = productRepository.findByStoreId(storeId, pageable);
        return products.map(ProductResponse::of);
    }

    // 상품 상태 변경 (MASTER, OWNER)
    @Transactional
    public ProductResponse updateStatus(UpdateProductStatusRequest request, String userId, UserRole role) {
        Product product = findProductById(request.productId());
        Store store = product.getStore();

        // 권한 검증
        validatePermission(store, userId, role);

        product.updateStatus(request.status());
        return ProductResponse.of(product);
    }

    // 상품 이름, 가격, 설명 변경 (MASTER, OWNER)
    @Transactional
    public ProductResponse updateDetails(UpdateProductDetailsRequest request, String userId, UserRole role) {
        Product product = findProductById(request.productId());

        Store store = product.getStore();

        // 권한 검증
        validatePermission(store, userId, role);

        product.updateDetails(request.name(), request.price(), request.description());
        return ProductResponse.of(product);
    }

    // 상품 삭제 (MASTER, OWNER)
    @Transactional
    public void deleteProduct(Long id, String userId, UserRole role) {
        Product product = findProductById(id);
        Store store = product.getStore();

        // 권한 검증
        validatePermission(store, userId, role);

        productRepository.delete(product);
    }






    // ID로 상점을 찾는 공통 메서드
    private Store findStoreById(Long id) {
        return storeRepository.findById(id).orElseThrow(
                () -> new StoreException(StoreErrorCode.STORE_NOT_FOUND)
        );
    }

    // ID로 상품을 찾는 공통 메서드
    private Product findProductById(Long id) {
        return productRepository.findById(id).orElseThrow(
                () -> new ProductException(ProductErrorCode.PRODUCT_NOT_FOUND)
        );
    }

    // 권한 검증 메서드
    private void validatePermission(Store store, String userId, UserRole role) {
        if (role == UserRole.OWNER) {
            // OWNER일 경우 가게 주인 여부 확인
            if (!store.getOwnerId().equals(userId)) {
                throw new StoreException(StoreErrorCode.UNAUTHORIZED_ACCESS);  // 권한 없는 경우 예외 처리
            }
        } else if (role == UserRole.USER) {
            throw new StoreException(StoreErrorCode.UNAUTHORIZED_ACCESS);
        }
    }


}
