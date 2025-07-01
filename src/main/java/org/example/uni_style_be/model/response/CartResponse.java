package org.example.uni_style_be.model.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartResponse {
    private ProductDetailResponse productDetail;
    private Integer quantity;

}
