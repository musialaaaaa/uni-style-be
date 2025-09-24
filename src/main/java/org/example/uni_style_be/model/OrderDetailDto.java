package org.example.uni_style_be.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.example.uni_style_be.model.response.ProductDetailResponse;

import java.math.BigDecimal;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderDetailDto {
    Integer quantity;

    BigDecimal priceAtTime;

    ProductDetailResponse productDetail;
}
