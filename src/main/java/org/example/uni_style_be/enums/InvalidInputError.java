package org.example.uni_style_be.enums;

import lombok.RequiredArgsConstructor;
import org.example.uni_style_be.exception.ResponseError;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
public enum InvalidInputError implements ResponseError {
    INVALID_INPUT("Tham số không hợp lệ"),
    PRODUCT_DETAIL_NOT_EXIST("Sản phẩm không tồn tại"),
    QUANTITY_IS_NOT_ENOUGH("Số lượng không đủ, chỉ còn lại {0}"),
    CART_DETAIL_NOT_FOUND("Không tìm thấy sản phẩm trong giỏ hàng"),
    CART_NOT_EXIST("Giỏ hàng của bạn hiện chưa tồn tại");

    ;

    private final String message;

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public int getHttpStatus() {
        return HttpStatus.BAD_REQUEST.value();
    }

    @Override
    public String getName() {
        return this.name();
    }

}
