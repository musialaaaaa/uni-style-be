package org.example.uni_style_be.uni_style_be.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ResponseException extends RuntimeException {
    private ResponseError error;
    private Object[] params;
    private Object data;

    public ResponseException(ResponseError error, Object... params) {
        this.error = error;
        this.params = params;
    }
}
