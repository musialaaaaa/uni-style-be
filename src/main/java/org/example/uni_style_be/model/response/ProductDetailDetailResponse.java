package org.example.uni_style_be.model.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.example.uni_style_be.enums.ProductDetailStatus;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductDetailDetailResponse extends BaseResponse {

    String code;

    String name;

    Integer quantity;

    BigDecimal price;

    String description;

    ProductDetailStatus status;

    ProductResponse product;

    MaterialResponse material;

    ColorResponse color;

    SizeResponse size;

    List<ImageResponse> images =  new ArrayList<>();

}
