package org.example.uni_style_be.uni_style_be.enums;

import lombok.RequiredArgsConstructor;
import org.example.uni_style_be.uni_style_be.exception.ResponseError;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
public enum UnauthorizedError implements ResponseError {
    UNAUTHORIZED("Thông tin xác thực không hợp lệ"),
    TOKEN_EXPIRED("Token đã hết hạn"),
    TOKEN_INVALID("Token không hợp lệ"),
    REFRESH_TOKEN_EXPIRED("Refresh token đã hết hạn");

    private final String message;

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public int getHttpStatus() {
        return HttpStatus.UNAUTHORIZED.value();
    }

    @Override
    public String getName() {
        return this.name();
    }

}
