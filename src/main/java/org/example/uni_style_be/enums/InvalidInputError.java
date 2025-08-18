package org.example.uni_style_be.enums;

import lombok.RequiredArgsConstructor;
import org.example.uni_style_be.exception.ResponseError;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
public enum InvalidInputError implements ResponseError {
    INVALID_INPUT("Tham số không hợp lệ"),
    CART_DETAIL_NOT_FOUND("Không tìm thấy sản phẩm trong giỏ hàng"),
    COUPON_EXPIRED("Mã giảm giá đã hết hạn hoặc lượt sử dụng"),
    COUPON_NOT_FOUND("Mã giảm giá không tồn tại"),
    PRODUCT_QUANTITY_IS_NOT_ENOUGH("Số lượng sản phẩm {0} không đủ, chỉ còn lại {1}"),
    ORDER_STATUS_INVALID("Không thể hủy đơn hàng với trạng thái hiện tại"),
    ORDER_NOT_FOUND("Đơn hàng không tồn tại"),
    PAYMENT_NOT_FOUND("Thanh toán không tồn tại"),
    PRODUCT_DETAIL_NOT_EXIST("Sản phẩm không tồn tại"),
    QUANTITY_IS_NOT_ENOUGH("Số lượng không đủ, chỉ còn lại {0}"),
    CART_NOT_EXIST("Giỏ hàng của bạn hiện chưa tồn tại"),
    ACCOUNT_NOT_FOUND("Tài khoản không tồn tại"),
    ROLE_NOT_FOUND("Quyền hạn không tồn tại"),
    FILE_TOO_LARGE("Tập tin quá lớn"),
    INVALID_FILE("Tệp tin không hợp lệ"),
    INVALID_FILE_TYPE("Định dạng tệp tin không hợp lệ, chỉ hỗ trợ định dạng {0}"),
    CATEGORY_NOT_FOUND("Danh mục không tồn tại"),
    PRODUCT_NOT_FOUND("Sản phẩm không tồn tại"),
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
