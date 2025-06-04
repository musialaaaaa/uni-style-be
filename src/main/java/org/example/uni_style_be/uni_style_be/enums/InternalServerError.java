package org.example.uni_style_be.uni_style_backend.enums;

import lombok.RequiredArgsConstructor;
import org.example.uni_style_be.uni_style_backend.exception.ResponseError;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
public enum InternalServerError implements ResponseError {
    INTERNAL_SERVER_ERROR("Hệ thống gặp sự cố, vui lòng thử lại sau"),
    DATA_ACCESS_EXCEPTION("Truy cập dữ liệu gặp sự cố, vui lòng thử lại sau"),
    ;

    private final String message;

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public int getHttpStatus() {
        return HttpStatus.INTERNAL_SERVER_ERROR.value();
    }

    @Override
    public String getName() {
        return this.name();
    }

}
