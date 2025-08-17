package org.example.uni_style_be.model.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.example.uni_style_be.entities.*;

import java.util.Set;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductDetailResponse extends BaseResponse {

    String code;

    String name;

    Integer quantity;

    Double price;

    String description;

//  Product product;

    Category category;

    Material material;

    Color color;

    Size size;

    Set<Image> images;
}
