package org.example.uni_style_be.exception;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.example.uni_style_be.enums.InternalServerError;
import org.example.uni_style_be.enums.InvalidInputError;
import org.example.uni_style_be.enums.UnauthorizedError;
import org.example.uni_style_be.model.response.ErrorResponse;
import org.example.uni_style_be.model.response.FieldErrorResponse;
import org.example.uni_style_be.model.response.InvalidInputResponse;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.NonTransientDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.text.MessageFormat;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
@Slf4j
public class GlobalExceptionHandler {

    private void logException(HttpServletRequest request, Throwable ex) {
        log.error("Failed to handle request {} {}: {}", request.getMethod(), request.getRequestURI(), ex.getMessage(), ex);
    }

    private void logWarn(HttpServletRequest request, Throwable ex) {
        log.warn("Failed to handle request {} {}: {}", request.getMethod(), request.getRequestURI(), ex.getMessage(), ex);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public InvalidInputResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        BindingResult bindingResult = e.getBindingResult();
        Set<FieldErrorResponse> fieldErrors = bindingResult.getAllErrors().stream()
                .map(objectError -> {
                    try {
                        FieldError fieldError = (FieldError) objectError;
                        String message = fieldError.getDefaultMessage();
                        return FieldErrorResponse.builder()
                                .field(fieldError.getField())
                                .objectName(fieldError.getObjectName())
                                .message(message)
                                .build();
                    } catch (ClassCastException ex) {
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
        return new InvalidInputResponse(
                InvalidInputError.INVALID_INPUT.getMessage(),
                InvalidInputError.INVALID_INPUT.name(),
                fieldErrors
        );
    }

    @ExceptionHandler({DataIntegrityViolationException.class, NonTransientDataAccessException.class, DataAccessException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse<Void> handleDataAccessException(DataAccessException e, HttpServletRequest request) {
        logException(request, e);
        ResponseError error = InternalServerError.DATA_ACCESS_EXCEPTION;
        return ErrorResponse.<Void>builder()
                .error(error.getName())
                .message(error.getMessage())
                .build();
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse<Void> handleResponseException(Exception e, HttpServletRequest request) {
        logException(request, e);
        ResponseError error = InternalServerError.INTERNAL_SERVER_ERROR;
        return ErrorResponse.<Void>builder()
                .error(error.getName())
                .message(error.getMessage())
                .build();
    }

    @ExceptionHandler(ResponseException.class)
    public ResponseEntity<ErrorResponse<Object>> handleResponseException(ResponseException e, HttpServletRequest request) {
        logWarn(request, e);
        ResponseError error = e.getError();
        String message = MessageFormat.format(error.getMessage(), e.getParams());
        ErrorResponse<Object> errorResponse = ErrorResponse.builder()
                .error(error.getName())
                .message(message)
                .data(e.getData())
                .build();
        return ResponseEntity.status(error.getHttpStatus()).body(errorResponse);
    }

    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorResponse<Void> handleAuthenticationException(AuthenticationException e) {
        ResponseError error = UnauthorizedError.UNAUTHORIZED;
        return ErrorResponse.<Void>builder()
                .error(error.getName())
                .message(error.getMessage())
                .build();
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public InvalidInputResponse handleHttpMessageNotReadableException(HttpMessageNotReadableException e, HttpServletRequest request) {
        logException(request, e);
        return new InvalidInputResponse(
                InvalidInputError.INVALID_INPUT.getMessage(),
                InvalidInputError.INVALID_INPUT.name(),
                null
        );
    }

}
