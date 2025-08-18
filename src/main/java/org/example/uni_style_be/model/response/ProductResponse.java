package org.example.uni_style_be.model.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductResponse extends BaseResponse {

    String code;

    String name;

    String description;

    String imageFileName;

    BigDecimal price = BigDecimal.ZERO;

    CategoryResponse category;

}
