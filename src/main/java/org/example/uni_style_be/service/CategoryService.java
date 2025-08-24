package org.example.uni_style_be.service;

import com.fasterxml.jackson.databind.JsonMappingException;
import org.example.uni_style_be.model.filter.CategoryParam;
import org.example.uni_style_be.model.request.CategoryRequest;
import org.example.uni_style_be.model.response.CategoryResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CategoryService {

    CategoryResponse create(CategoryRequest categoryRequest);

    CategoryResponse update(Long id, CategoryRequest categoryRequest) throws JsonMappingException;

    void delete(Long id);

    CategoryResponse detail(Long id);

    Page<CategoryResponse> filter(CategoryParam param, Pageable pageable);
}
