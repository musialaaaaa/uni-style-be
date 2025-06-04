package org.example.uni_style_be.uni_style_backend.service.impl;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.example.uni_style_be.uni_style_backend.properties.UtilProperties;
import org.example.uni_style_be.uni_style_backend.service.JwtService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtServiceImpl implements JwtService {
    @Override
    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    @Override
    public String generateToken(
            Map<String, Object> claims,
            UserDetails userDetails
    ) {
        long tokenExpiration = UtilProperties.jwt().getTokenExpiration();
        return buildToken(claims, userDetails, tokenExpiration);
    }

    @Override
    public String generateRefreshToken(
            UserDetails userDetails
    ) {
        return buildToken(new HashMap<>(), userDetails, UtilProperties.jwt().getRefreshTokenExpiration());
    }

    @Override
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    @Override
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()));
    }

    private SecretKey createSecretKey() {
        byte[] keyBytes = UtilProperties.jwt().getSecretKey().getBytes(StandardCharsets.UTF_8);
        return new SecretKeySpec(keyBytes, "HmacSHA256");
    }

    private String buildToken(Map<String, Object> claims, UserDetails userDetails, long expiration) {
        return Jwts.builder()
                .subject(userDetails.getUsername())
                .claims(claims)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(createSecretKey())
                .compact();
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(createSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();

    }
}
