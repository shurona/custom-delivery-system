package com.webest.store.category.api;

import com.webest.store.category.api.dto.CategoryResponse;
import com.webest.store.category.api.dto.CreateCategoryRequest;
import com.webest.store.category.application.CategoryService;
import com.webest.web.response.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/categories")
public class CategoryController {
    private final CategoryService categoryService;

    // 카테고리 생성
    @PostMapping
    public CommonResponse<CategoryResponse> saveCategory(CreateCategoryRequest createCategoryRequest) {
         CategoryResponse categoryResponse = categoryService.saveCategory(createCategoryRequest);
        return CommonResponse.success(categoryResponse);
    }

    // 카테고리 단건 조회
    @GetMapping("{id}")
    public CommonResponse<CategoryResponse> getCategoryById(@PathVariable("id") Long id) {
        CategoryResponse categoryResponse = categoryService.getCategoryById(id);
        return CommonResponse.success(categoryResponse);
    }

    // 카테고리 전체 조회
    @GetMapping
    public CommonResponse<List<CategoryResponse>> getAllCategories() {
        List<CategoryResponse> categoryResponses = categoryService.getAllCategories();
        return CommonResponse.success(categoryResponses);
    }

}
