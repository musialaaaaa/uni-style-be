package org.example.uni_style_be.service;

import org.springframework.security.core.userdetails.UserDetails;

import java.util.Map;

public interface JwtService {
    String generateToken(UserDetails userDetails);

    String generateToken(
            Map<String, Object> claims,
            UserDetails userDetails
    );

    String generateRefreshToken(
            UserDetails userDetails
    );

    String extractUsername(String token);

    boolean isTokenValid(String token, UserDetails userDetails);
}
