package org.example.uni_style_be.uni_style_backend.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.uni_style_be.uni_style_backend.model.request.AuthenticationRequest;
import org.example.uni_style_be.uni_style_backend.model.response.AuthenticationResponse;

import java.io.IOException;

public interface AuthenticationService {

    AuthenticationResponse authenticate(AuthenticationRequest request);

    void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException;
}
