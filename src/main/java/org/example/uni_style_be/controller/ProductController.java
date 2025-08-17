package org.example.uni_style_be.controller;

import com.fasterxml.jackson.databind.JsonMappingException;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.uni_style_be.entities.Product;
import org.example.uni_style_be.model.filter.ProductParam;
import org.example.uni_style_be.model.request.ProductRequest;
import org.example.uni_style_be.model.response.ProductResponse;
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
    public PageUtils<ProductResponse> filter(ProductParam param, Pageable pageable) {
        return new PageUtils<>(productService.filter(param, pageable));
    }

    @PostMapping
    public ProductResponse create(@Valid @RequestBody ProductRequest productRequest) {
        return productService.create(productRequest);
    }

    @PutMapping("/{id}")
    public ProductResponse update(
            @PathVariable Long id, @Valid @RequestBody ProductRequest productRequest) throws JsonMappingException {
        return productService.update(id, productRequest);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        productService.delete(id);
    }

    @GetMapping("/{id}")
    public Product findById(@PathVariable Long id) {
        return productService.findById(id);
    }
}
