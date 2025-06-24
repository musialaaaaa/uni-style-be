package org.example.uni_style_be.exception;

public interface ResponseError {
    String getMessage();

    int getHttpStatus();

    String getName();
}
