package org.example.uni_style_be.mapper;

import org.example.uni_style_be.entities.ProductDetail;
import org.example.uni_style_be.model.response.ProductDetailDetailResponse;
import org.example.uni_style_be.model.response.ProductDetailResponse;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface ProductDetailMapper {

    ProductDetailResponse toProductDetailResponse(ProductDetail productDetail);

    List<ProductDetailResponse> toProductDetailResponse(List<ProductDetail> productDetails);

    ProductDetailDetailResponse toProductDetailDetailResponse(ProductDetail productDetail);
}
