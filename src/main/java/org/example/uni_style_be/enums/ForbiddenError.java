package org.example.uni_style_be.enums;

import lombok.RequiredArgsConstructor;
import org.example.uni_style_be.exception.ResponseError;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
public enum ForbiddenError implements ResponseError {
    FORBIDDEN("Bạn không có quyền thực hiện hành động này"),
    ;

    private final String message;

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public int getHttpStatus() {
        return HttpStatus.FORBIDDEN.value();
    }

    @Override
    public String getName() {
        return this.name();
    }

}
