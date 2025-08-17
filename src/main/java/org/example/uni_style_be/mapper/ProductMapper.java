package org.example.uni_style_be.mapper;

import org.example.uni_style_be.entities.Product;
import org.example.uni_style_be.model.response.ProductResponse;
import org.mapstruct.Mapper;

@Mapper
public interface ProductMapper {

    ProductResponse toProductResponse(Product product);
}
