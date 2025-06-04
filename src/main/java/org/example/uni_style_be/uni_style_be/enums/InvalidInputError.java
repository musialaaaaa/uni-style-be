package org.example.uni_style_be.uni_style_be.enums;

import lombok.RequiredArgsConstructor;
import org.example.uni_style_be.uni_style_be.exception.ResponseError;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
public enum InvalidInputError implements ResponseError {
    INVALID_INPUT("Tham số không hợp lệ"),
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
