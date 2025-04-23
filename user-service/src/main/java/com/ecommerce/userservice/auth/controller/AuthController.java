package com.ecommerce.userservice.auth.controller;

import com.ecommerce.userservice.auth.model.dto.request.RefreshTokenRequestDto;
import com.ecommerce.userservice.auth.model.dto.request.RegistrationRequestDto;
import com.ecommerce.userservice.auth.model.dto.response.JwtAuthenticationResponseDto;
import com.ecommerce.userservice.auth.model.dto.request.LoginRequestDto;
import com.ecommerce.userservice.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Validated
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<JwtAuthenticationResponseDto> login(@RequestBody LoginRequestDto loginRequestDto) {
        return ResponseEntity.ok(authService.login(loginRequestDto));
    }

    @PostMapping("/register")
    public ResponseEntity<JwtAuthenticationResponseDto> register(@RequestBody RegistrationRequestDto registrationRequestDto) {
        return ResponseEntity.ok(authService.register(registrationRequestDto));
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<JwtAuthenticationResponseDto> refreshToken(@RequestBody RefreshTokenRequestDto refreshTokenRequestDto) {
        return ResponseEntity.ok(authService.refreshToken(refreshTokenRequestDto));
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestBody RefreshTokenRequestDto refreshTokenRequestDto) {
        authService.logout(refreshTokenRequestDto);
        return ResponseEntity.noContent().build();
    }

}
