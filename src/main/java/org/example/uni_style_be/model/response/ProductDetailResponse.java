package org.example.uni_style_be.model.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import org.example.uni_style_be.entities.*;

import java.util.Set;


@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductDetailResponse extends BaseResponse {

  String code;

  String name;

  Integer quantity;

  Double price;

  String image;

  String description;

  Boolean isDeleted;

  Product product;

  Category category;

  Material material;

  Brand brand;

  Color color;

  Size size;

  Set<Image> images;
}
