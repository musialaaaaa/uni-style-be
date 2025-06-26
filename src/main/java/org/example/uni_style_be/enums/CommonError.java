package org.example.uni_style_be.enums;


import lombok.AllArgsConstructor;
import lombok.Getter;
import org.example.uni_style_be.exception.ResponseError;

@Getter
@AllArgsConstructor
public enum CommonError implements ResponseError {
    DATA_NOT_FOUND("Dữ liệu không tồn tại", 404),
    INVALID_DATA("Dữ liệu không hợp lệ", 400),
    COUPON_EXPIRED("Voucher đã hết hạn", 400),
    COUPON_LIMIT_REACHED("Voucher đã đạt giới hạn sử dụng", 400);

    private final String message;
    private final int httpStatus;

    @Override
    public String getName() {
        return this.name();
    }
}
