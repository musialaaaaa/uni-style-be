package org.example.uni_style_be.mapper;

import org.example.uni_style_be.entities.Category;
import org.example.uni_style_be.model.response.CategoryResponse;
import org.mapstruct.Mapper;

@Mapper
public interface CategoryMapper {
    CategoryResponse toCategoryResponse(Category category);
}
