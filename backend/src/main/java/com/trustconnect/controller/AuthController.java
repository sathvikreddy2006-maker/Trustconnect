package com.trustconnect.controller;

import com.trustconnect.dto.LoginRequest;
import com.trustconnect.dto.LoginResponse;
import com.trustconnect.dto.TrustRegistrationRequest;
import com.trustconnect.service.AuthService;
import com.trustconnect.service.TrustRegistrationService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(
    origins = "http://localhost:5173",
    allowCredentials = "true"
)
public class AuthController {

    private final AuthService authService;
    private final TrustRegistrationService trustRegistrationService;

    public AuthController(
            AuthService authService,
            TrustRegistrationService trustRegistrationService) {
        this.authService = authService;
        this.trustRegistrationService = trustRegistrationService;
    }

    @PostMapping("/login")
    public LoginResponse login(
            @RequestBody LoginRequest request,
            HttpServletRequest httpRequest) {

        return authService.login(
                request,
                httpRequest
        );
    }

    @PostMapping("/register-trust")
    public void registerTrust(@Valid @RequestBody TrustRegistrationRequest request) {
        trustRegistrationService.registerTrust(request);
    }

    @PostMapping("/logout")
    public String logout(HttpServletRequest request) {

        var session = request.getSession(false);

        if (session != null) {
            session.invalidate();
        }

        return "Logged out successfully";
    }
}