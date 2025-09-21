package org.example.uni_style_be.controller;

import com.fasterxml.jackson.databind.JsonMappingException;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.uni_style_be.model.filter.ProductDetailParam;
import org.example.uni_style_be.model.request.ProductDetailRequest;
import org.example.uni_style_be.model.response.ProductDetailDetailResponse;
import org.example.uni_style_be.model.response.ProductDetailResponse;
import org.example.uni_style_be.service.ProductDetailService;
import org.example.uni_style_be.utils.PageUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/product-details")
@RequiredArgsConstructor
@Tag(name = "Api sản phẩm chi tiết")
public class ProductDetailController {
    private final ProductDetailService productDetailService;

    @GetMapping
    public PageUtils<ProductDetailResponse> filter(ProductDetailParam param, Pageable pageable) {
        return new PageUtils<>(productDetailService.filter(param, pageable));
    }

    @PostMapping
    public ProductDetailResponse create(@Valid @RequestBody ProductDetailRequest req) {
        return productDetailService.create(req);
    }

    @PutMapping("/{id}")
    public ProductDetailResponse update(
            @PathVariable Long id, @Valid @RequestBody ProductDetailRequest req) throws JsonMappingException {
        return productDetailService.update(id, req);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        productDetailService.delete(id);
    }

    @GetMapping("/{id}")
    public ProductDetailDetailResponse detail(@PathVariable Long id) {
        return productDetailService.detail(id);
    }
}
