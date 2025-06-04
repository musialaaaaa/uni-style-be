package org.example.uni_style_be.uni_style_backend.exception;

public interface ResponseError {
    String getMessage();

    int getHttpStatus();

    String getName();
}
