package org.example.uni_style_be.service.impl;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.uni_style_be.entities.Category;
import org.example.uni_style_be.enums.NotFoundError;
import org.example.uni_style_be.exception.ResponseException;
import org.example.uni_style_be.model.filter.CategoryParam;
import org.example.uni_style_be.model.request.CategoryRequest;
import org.example.uni_style_be.model.response.CategoryResponse;
import org.example.uni_style_be.repositories.CategoryRepository;
import org.example.uni_style_be.repositories.specification.CategorySpecification;
import org.example.uni_style_be.service.CategoryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final ObjectMapper objectMapper;

    @Override
    @Transactional
    public CategoryResponse create(CategoryRequest categoryRequest) {
        Category category = objectMapper.convertValue(categoryRequest, Category.class);
        return objectMapper.convertValue(categoryRepository.save(category), CategoryResponse.class);
    }

    @Override
    @Transactional
    public CategoryResponse update(Long id, CategoryRequest categoryRequest) throws JsonMappingException {
        Category category = findById(id);
        objectMapper.updateValue(category, categoryRequest);
        return objectMapper.convertValue(categoryRepository.save(category), CategoryResponse.class);
    }

    @Override
    public void delete(Long id) {
        categoryRepository.deleteById(id);
    }

    @Override
    public Category findById(Long id) {
        return categoryRepository
                .findById(id)
                .orElseThrow(() -> new ResponseException(NotFoundError.CATEGORY_NOT_FOUND));
    }

    @Override
    public Page<CategoryResponse> filter(CategoryParam param, Pageable pageable) {
        Specification<Category> categorySpe = CategorySpecification.filterSpec(param);
        Page<Category> categoryPage = categoryRepository.findAll(categorySpe, pageable);
        return categoryPage.map(category -> objectMapper.convertValue(category, CategoryResponse.class));
    }
}
