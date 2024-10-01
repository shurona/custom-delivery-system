package com.webest.store.product.api;

import com.webest.store.product.api.dto.CreateProductRequest;
import com.webest.store.product.api.dto.ProductResponse;
import com.webest.store.product.application.ProductService;
import com.webest.web.response.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/products")
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public CommonResponse<ProductResponse> saveProduct(@RequestBody CreateProductRequest request) {
        ProductResponse response = productService.saveProduct(request);
        return CommonResponse.success(response);
    }

    @GetMapping("{id}")
    public CommonResponse<ProductResponse> getProductById(@PathVariable("id") Long id) {
        ProductResponse response = productService.getProductById(id);
        return CommonResponse.success(response);
    }
}
