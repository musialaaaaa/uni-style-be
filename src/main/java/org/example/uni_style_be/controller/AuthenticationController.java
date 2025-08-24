package org.example.uni_style_be.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.uni_style_be.model.request.AuthenticationRequest;
import org.example.uni_style_be.model.request.ChangePasswordRequest;
import org.example.uni_style_be.model.request.RegisterRequest;
import org.example.uni_style_be.model.response.AuthenticationResponse;
import org.example.uni_style_be.model.response.ServiceResponse;
import org.example.uni_style_be.service.AuthenticationService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Xác thực người dùng")
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping("/authenticate")
    @Operation(summary = "Đăng nhập")
    public ServiceResponse<AuthenticationResponse> authenticate(@RequestBody @Valid AuthenticationRequest request) {
        return ServiceResponse.ok(authenticationService.authenticate(request));
    }

    @PostMapping("/register")
    @Operation(summary = "Đăng ký")
    public ServiceResponse<AuthenticationResponse> register(@RequestBody @Valid RegisterRequest request) {
        return ServiceResponse.ok(authenticationService.register(request));
    }

    @PostMapping("/refresh-token")
    @Operation(summary = "Refresh token")
    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        authenticationService.refreshToken(request, response);
    }

    @PostMapping("/change-password")
    @Operation(summary = "Đổi mật khẩu")
    public ServiceResponse<Void> changePassword(@RequestBody @Valid ChangePasswordRequest request) {
        return ServiceResponse.ok(authenticationService.changePassword(request));
    }

}
