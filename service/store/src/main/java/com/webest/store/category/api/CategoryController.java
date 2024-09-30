package com.webest.store.category.api;

import com.webest.store.category.api.dto.CategoryResponse;
import com.webest.store.category.api.dto.CreateCategoryRequest;
import com.webest.store.category.application.CategoryService;
import com.webest.web.response.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/categories")
public class CategoryController {
    private final CategoryService categoryService;

    @PostMapping
    public CommonResponse<CategoryResponse> saveCategory(CreateCategoryRequest createCategoryRequest) {
         CategoryResponse categoryResponse = categoryService.saveCategory(createCategoryRequest);
        return CommonResponse.success(categoryResponse);
    }

}
