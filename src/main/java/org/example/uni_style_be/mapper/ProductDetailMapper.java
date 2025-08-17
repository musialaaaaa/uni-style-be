package org.example.uni_style_be.mapper;

import org.example.uni_style_be.entities.ProductDetail;
import org.example.uni_style_be.model.response.ProductDetailResponse;
import org.mapstruct.Mapper;

@Mapper
public interface ProductDetailMapper {

    ProductDetailResponse toProductDetailResponse(ProductDetail productDetail);
}
