package org.example.uni_style_be.controller;

import com.fasterxml.jackson.databind.JsonMappingException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.uni_style_be.model.filter.ProductParam;
import org.example.uni_style_be.model.request.ProductRequest;
import org.example.uni_style_be.model.response.ProductDetailShopResponse;
import org.example.uni_style_be.model.response.ProductResponse;
import org.example.uni_style_be.model.response.ServiceResponse;
import org.example.uni_style_be.service.ProductService;
import org.example.uni_style_be.utils.PageUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
@Tag(name = "Api sản phẩm")
public class ProductController {
    private final ProductService productService;

    @GetMapping
    public ServiceResponse<PageUtils<ProductResponse>> filter(ProductParam param, Pageable pageable) {
        return ServiceResponse.ok(new PageUtils<>(productService.filter(param, pageable)));
    }

    @PostMapping
    public ServiceResponse<ProductResponse> create(@Valid @RequestBody ProductRequest productRequest) {
        return ServiceResponse.ok(productService.create(productRequest));
    }

    @PutMapping("/{id}")
    public ServiceResponse<ProductResponse> update(
            @PathVariable Long id, @Valid @RequestBody ProductRequest productRequest) throws JsonMappingException {
        return ServiceResponse.ok(productService.update(id, productRequest));
    }

    @DeleteMapping("/{id}")
    public ServiceResponse<Void> delete(@PathVariable Long id) {
        return ServiceResponse.ok(productService.delete(id));
    }

    @GetMapping("/{id}")
    public ServiceResponse<ProductResponse> findById(@PathVariable Long id) {
        return ServiceResponse.ok(productService.detail(id));
    }

    @Operation(summary = "Detail cho giao diện shop")
    @GetMapping("/{id}/shop")
    public ServiceResponse<ProductDetailShopResponse> detailShop(@PathVariable Long id) {
        return ServiceResponse.ok(productService.detailShop(id));
    }
}
