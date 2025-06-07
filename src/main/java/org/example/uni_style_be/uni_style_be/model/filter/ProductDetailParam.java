package org.example.uni_style_be.uni_style_be.model.filter;

import lombok.Data;

@Data
public class ProductDetailParam {
  private String code;

  private String name;

  private String description;

  private Integer quantity;

  private Double price;

  private String image;

  private Long productId;

  private Long materialId;

  private Long brandId;

  private Long colorId;

  private Long sizeId;
}
