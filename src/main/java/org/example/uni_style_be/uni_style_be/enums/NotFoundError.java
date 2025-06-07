package org.example.uni_style_be.uni_style_be.enums;

import lombok.RequiredArgsConstructor;
import org.example.uni_style_be.uni_style_be.exception.ResponseError;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
public enum NotFoundError implements ResponseError {
  MATERIAL_NOT_FOUND("Chất liệu không được để trống"),
  SIZE_NOT_FOUND("Kích thước không được để trống"),
  COLOR_NOT_FOUND("Màu không được để trống"),
  BRAND_NOT_FOUND("Thương hiệu không được để trống"),
  PRODUCT_NOT_FOUND("Sản phẩm không được để trống"),
  PRODUCT_DETAIL_NOT_FOUND("Sản phẩm chi tiet không được để trống");

  private final String message;

  @Override
  public String getMessage() {
    return this.message;
  }

  @Override
  public int getHttpStatus() {
    return HttpStatus.NOT_FOUND.value();
  }

  @Override
  public String getName() {
    return this.name();
  }
}
