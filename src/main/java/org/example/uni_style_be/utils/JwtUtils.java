package org.example.uni_style_be.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.example.uni_style_be.enums.UnauthorizedError;
import org.example.uni_style_be.model.response.ErrorResponse;
import org.springframework.http.MediaType;

import java.io.IOException;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class JwtUtils {
    public static void handleUnauthorized(HttpServletResponse response, UnauthorizedError error, JwtException exception) throws IOException {
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        ErrorResponse<Object> errorResponse = ErrorResponse.builder()
                .error(error.name())
                .message(error.getMessage())
                .data(exception == null ? null : exception.getMessage())
                .build();
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonResponse = objectMapper.writeValueAsString(errorResponse);
        response.getWriter().write(jsonResponse);
    }
}
