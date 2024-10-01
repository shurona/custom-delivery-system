package com.webest.store.product.application;

import com.webest.store.product.api.dto.CreateProductRequest;
import com.webest.store.product.api.dto.ProductResponse;
import com.webest.store.product.domain.Product;
import com.webest.store.product.domain.ProductRepository;
import com.webest.store.product.domain.ProductStatus;
import com.webest.store.product.exception.ProductErrorCode;
import com.webest.store.product.exception.ProductException;
import com.webest.store.store.domain.Store;
import com.webest.store.store.domain.StoreRepository;
import com.webest.store.store.exception.StoreErrorCode;
import com.webest.store.store.exception.StoreException;
import lombok.RequiredArgsConstructor;
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
    public ProductResponse saveProduct(CreateProductRequest request) {
        Store store = findStoreById(request.storeId());
        Product product = Product.of(store, request.name(), request.price(), request.description());
        product.updateStatus(ProductStatus.AVAILABLE);
        productRepository.save(product);
        return ProductResponse.of(product);
    }


    // 상품 단건 조회
    public ProductResponse getProductById(Long id) {
        Product product = findProductById(id);
        return ProductResponse.of(product);
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


}
