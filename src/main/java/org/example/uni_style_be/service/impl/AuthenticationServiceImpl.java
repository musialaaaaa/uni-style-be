package org.example.uni_style_be.service.impl;


import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.uni_style_be.entities.Account;
import org.example.uni_style_be.entities.Token;
import org.example.uni_style_be.enums.InvalidInputError;
import org.example.uni_style_be.enums.UnauthorizedError;
import org.example.uni_style_be.exception.ResponseException;
import org.example.uni_style_be.mapper.AccountMapper;
import org.example.uni_style_be.model.request.AuthenticationRequest;
import org.example.uni_style_be.model.request.RegisterRequest;
import org.example.uni_style_be.model.response.AuthenticationResponse;
import org.example.uni_style_be.model.response.ServiceResponse;
import org.example.uni_style_be.repositories.AccountRepository;
import org.example.uni_style_be.repositories.TokenRepository;
import org.example.uni_style_be.service.AuthenticationService;
import org.example.uni_style_be.service.JwtService;
import org.example.uni_style_be.utils.JwtUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationServiceImpl implements AuthenticationService {
    AccountRepository accountRepository;
    JwtService jwtService;
    TokenRepository tokenRepository;
    AuthenticationManager authenticationManager;
    AccountMapper accountMapper;
    PasswordEncoder passwordEncoder;

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );
        Account account = accountRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new ResponseException(UnauthorizedError.UNAUTHORIZED));

        return saveToken(account);
    }

    @Override
    public AuthenticationResponse register(RegisterRequest rq) {
        if (!rq.getPassword().equals(rq.getConfirmPassword())) {
            throw new ResponseException(InvalidInputError.PASSWORD_NOT_MATCH);
        }

        Account account = accountMapper.toAccount(rq);
        account.setPassword(passwordEncoder.encode(rq.getPassword()));
        Account accountSaved = accountRepository.save(account);
        return saveToken(accountSaved);
    }

    @Override
    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String username;
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            JwtUtils.handleUnauthorized(response, UnauthorizedError.TOKEN_INVALID, null);
            return;
        }
        refreshToken = authHeader.substring(7);
        try {
            username = jwtService.extractUsername(refreshToken);
            if (username != null) {
                Account account = accountRepository.findByUsername(username)
                        .orElseThrow();
                if (jwtService.isTokenValid(refreshToken, account)) {
                    String accessToken = jwtService.generateToken(account);
                    Token tokenEntity = Token.builder()
                            .token(accessToken)
                            .account(account)
                            .build();
                    tokenRepository.save(tokenEntity);
                    ServiceResponse<AuthenticationResponse> authResponse = ServiceResponse.ok(
                            AuthenticationResponse.builder()
                                    .accessToken(accessToken)
                                    .refreshToken(refreshToken)
                                    .build()
                    );
                    new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
                }
            }
        } catch (ExpiredJwtException e) {
            JwtUtils.handleUnauthorized(response, UnauthorizedError.REFRESH_TOKEN_EXPIRED, e);
        } catch (JwtException e) {
            JwtUtils.handleUnauthorized(response, UnauthorizedError.TOKEN_INVALID, e);
        }

    }

    private AuthenticationResponse saveToken(Account account) {
        String accessToken = jwtService.generateToken(account);
        String refreshToken = jwtService.generateRefreshToken(account);
        Token tokenEntity = Token.builder()
                .token(accessToken)
                .account(account)
                .build();
        tokenRepository.save(tokenEntity);

        return AuthenticationResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }
}
