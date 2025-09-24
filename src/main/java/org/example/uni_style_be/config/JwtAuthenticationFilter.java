package org.example.uni_style_be.config;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.uni_style_be.entities.Token;
import org.example.uni_style_be.enums.UnauthorizedError;
import org.example.uni_style_be.repositories.TokenRepository;
import org.example.uni_style_be.service.JwtService;
import org.example.uni_style_be.utils.JwtUtils;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    JwtService jwtService;
    TokenRepository tokenRepository;
    UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {

        if (
                request.getServletPath().contains("/auth/authenticate") ||
                        request.getServletPath().contains("/auth/register") ||
                        request.getServletPath().contains("/auth/refresh-token")
        ) {
            filterChain.doFilter(request, response);
            return;
        }

        final String authHeader = request.getHeader("Authorization");
        final String token;
        final String userEmail;
        Token tokenEntity = null;
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
        token = authHeader.substring(7);

        try {
            tokenEntity = tokenRepository.findByToken(token)
                    .orElseThrow(() -> new JwtException(UnauthorizedError.TOKEN_INVALID.getMessage()));
            userEmail = jwtService.extractUsername(token);
            if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(userEmail);
                boolean isTokenValid = !tokenEntity.isExpired() && !tokenEntity.isRevoked();
                if (jwtService.isTokenValid(token, userDetails) && isTokenValid) {
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities()
                    );
                    authToken.setDetails(
                            new WebAuthenticationDetailsSource().buildDetails(request)
                    );
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }
        } catch (ExpiredJwtException e) {
            JwtUtils.handleUnauthorized(response, UnauthorizedError.TOKEN_EXPIRED, e);
            if (tokenEntity != null && !tokenEntity.isExpired()) {
                tokenRepository.updateExpiredByToken(true, token);
            }
            return;
        } catch (JwtException e) {
            JwtUtils.handleUnauthorized(response, UnauthorizedError.TOKEN_INVALID, e);
            return;
        }
        filterChain.doFilter(request, response);
    }

}
