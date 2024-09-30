package com.webest.store.category.application;

import com.webest.store.category.api.dto.CategoryResponse;
import com.webest.store.category.api.dto.CreateCategoryRequest;
import com.webest.store.category.domain.StoreCategory;
import com.webest.store.category.domain.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    @Transactional
    public CategoryResponse saveCategory(CreateCategoryRequest createCategoryRequest) {
        StoreCategory storeCategory = createCategoryRequest.toEntity();
        categoryRepository.save(storeCategory);
        return CategoryResponse.of(storeCategory);
    }

    // key 값으로 카테고리가 이미 존재하는지 확인하는 메서드
    private void checkIfCategoryExistsByKey(String key) {
        if (categoryRepository.existsByKey(key)) {
            throw new CategoryAlreadyExistsException("카테고리가 이미 존재합니다: " + key);
        }
    }

}
