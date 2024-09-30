package com.webest.store.category.application;

import com.webest.store.category.api.dto.CategoryResponse;
import com.webest.store.category.api.dto.CreateCategoryRequest;
import com.webest.store.category.domain.StoreCategory;
import com.webest.store.category.domain.CategoryRepository;
import com.webest.store.category.exception.CategoryErrorCode;
import com.webest.store.category.exception.CategoryException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;


    // 카테고리 생성
    @Transactional
    public CategoryResponse saveCategory(CreateCategoryRequest createCategoryRequest) {
        checkIfCategoryExistsByKey(createCategoryRequest.key());
        StoreCategory storeCategory = createCategoryRequest.toEntity();
        categoryRepository.save(storeCategory);
        return CategoryResponse.of(storeCategory);
    }

    // 카테고리 단건 조회
    public CategoryResponse getCategoryById(Long id) {
        StoreCategory storeCategory = findCategoryById(id);
        return CategoryResponse.of(storeCategory);
    }

    // 카테고리 전체 조회
    public List<CategoryResponse> getAllCategories() {
        return categoryRepository.findAll().stream()
                .map(CategoryResponse::of)
                .collect(Collectors.toList());
    }

    // key 값으로 카테고리가 이미 존재하는지 확인하는 메서드
    private void checkIfCategoryExistsByKey(String key) {
        if (categoryRepository.existsByKey(key)) {
            throw new CategoryException(CategoryErrorCode.CATEGORY_ALREADY_EXISTS);
        }
    }

    // ID로 카테고리를 찾는 공통 메서드
    private StoreCategory findCategoryById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryException(CategoryErrorCode.CATEGORY_NOT_FOUND));
    }
}
