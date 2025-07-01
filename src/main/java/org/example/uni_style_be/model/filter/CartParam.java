package org.example.uni_style_be.model.filter;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CartParam {
    private Long productDetailId;

    private String name;


}
