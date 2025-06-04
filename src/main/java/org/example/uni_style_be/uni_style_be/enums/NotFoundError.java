package org.example.uni_style_be.uni_style_backend.enums;

import lombok.RequiredArgsConstructor;
import org.example.uni_style_be.uni_style_backend.exception.ResponseError;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
public enum NotFoundError implements ResponseError {
    ;

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
