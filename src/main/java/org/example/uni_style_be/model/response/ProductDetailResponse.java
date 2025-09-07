package org.example.uni_style_be.model.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.example.uni_style_be.enums.ProductDetailStatus;

import java.util.List;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductDetailResponse extends BaseResponse {

    String code;

    String name;

    Integer quantity;

    Double price;

    String description;

    MaterialResponse material;

    ColorResponse color;

    SizeResponse size;

    List<ImageResponse> images;

    ProductDetailStatus status;

    ProductResponse product;
}
