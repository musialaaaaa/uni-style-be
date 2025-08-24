package org.example.uni_style_be.mapper;

import org.example.uni_style_be.entities.Product;
import org.example.uni_style_be.model.request.ProductRequest;
import org.example.uni_style_be.model.response.ProductDetailShopResponse;
import org.example.uni_style_be.model.response.ProductResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper
public interface ProductMapper {

    ProductResponse toProductResponse(Product product);

    Product toProduct(ProductRequest productRequest);

    void toProduct(@MappingTarget Product product, ProductRequest productRequest);

    @Mapping(target = "productDetails", ignore = true)
    ProductDetailShopResponse toProductDetailShopResponse(Product product);
}
