package org.example.uni_style_be.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.uni_style_be.model.request.AuthenticationRequest;
import org.example.uni_style_be.model.request.RegisterRequest;
import org.example.uni_style_be.model.response.AuthenticationResponse;

import java.io.IOException;

public interface AuthenticationService {

    AuthenticationResponse authenticate(AuthenticationRequest request);

    AuthenticationResponse register(RegisterRequest rq);

    void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException;
}
