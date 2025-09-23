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
    PASSWORD_NOT_MATCH("Mật khẩu không khớp"),
    ACCOUNT_EXIST("Tên tài khoản đã tồn tại"),
    CATEGORY_USING("Không thể thực hiện thao tác này do danh mục đang được sử dụng"),
    ONLY_DELETE_STAFF_ACCOUNT("Chỉ có thể xoá tài khoản của nhân viên"),
    START_TIME_GR_END_TIME("Thời gian bắt đầu phải nhỏ hơn thời gian kết thúc"),
    INVALID_DATE("Định dạng ngày không đúng, yêu cầu yyyy-MM-dd"),
    COUPON_HAS_EXIST("Mã giảm giá đã tồn tại"),
    COLOR_HAS_PRODUCT("Đang có sản phẩm sử dụng màu sắc này"),
    COUPON_HAS_ORDER("Đang có đơn hàng sử dụng mã giảm giá này"),
    MATERIAL_HAS_PRODUCT("Đang có sản phẩm sử dụng chất liệu này"),
    PRODUCT_HAS_ORDER("Sản phẩm này đã có đơn hàng"),
    PRODUCT_DETAIL_HAS_ORDER("Sản phẩm chi tiết này đã có đơn hàng"),
    SIZE_HAS_PRODUCT("Kích thước này đã có sản phẩm"),
    NO_PRODUCT_DETAIL_ACTIVE("Phải có ít nhất 1 sản phẩm chi tiết được kích hoạt để kích hoạt sản phẩm này")
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
