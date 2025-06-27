package org.example.uni_style_be.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.uni_style_be.entities.Category;
import org.example.uni_style_be.model.filter.CategoryParam;
import org.example.uni_style_be.model.request.CategoryRequest;
import org.example.uni_style_be.model.response.CategoryResponse;
import org.example.uni_style_be.service.CategoryService;
import org.example.uni_style_be.utils.PageUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/category")
@RequiredArgsConstructor
@Tag(name = "Api danh mục")
public class    CategoryController {
    private final CategoryService categoryService;

    @GetMapping
    public PageUtils<CategoryResponse> filter(CategoryParam categoryParam, Pageable pageable) {
        return new PageUtils<>(categoryService.filter(categoryParam, pageable));
    }

    @PostMapping
    public CategoryResponse create(@Valid @RequestBody CategoryRequest categoryReq) {
        return categoryService.create(categoryReq);
    }

    @PutMapping("/{id}")
    public CategoryResponse update(@PathVariable Long id, @Valid @RequestBody CategoryRequest categoryReq) throws JsonProcessingException {
        return categoryService.update(id, categoryReq);
    }

    @GetMapping("/{id}")
    public Category findById(@PathVariable Long id) {
        return categoryService.findById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        categoryService.delete(id);
    }
}
