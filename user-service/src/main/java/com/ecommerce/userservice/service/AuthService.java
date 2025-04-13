package com.ecommerce.userservice.service;

import com.ecommerce.userservice.client.KeycloakClient;
import com.ecommerce.userservice.model.dto.request.LoginRequestDto;
import com.ecommerce.userservice.model.dto.request.RefreshTokenRequestDto;
import com.ecommerce.userservice.model.dto.request.RegistrationRequestDto;
import com.ecommerce.userservice.model.dto.response.JwtAuthenticationResponseDto;
import com.ecommerce.userservice.service.processor.UserRegistrationAsyncProcessor;
import com.ecommerce.userservice.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final KeycloakClient keycloakClient;
    private final UserRegistrationAsyncProcessor asyncProcessor;

    public JwtAuthenticationResponseDto login(LoginRequestDto loginRequestDto) {
        return keycloakClient.loginUser(loginRequestDto);
    }

    public JwtAuthenticationResponseDto register(RegistrationRequestDto dto) {
        JwtAuthenticationResponseDto jwt = keycloakClient.createUser(dto);
        UUID keycloakUserId = JwtUtil.extractSubject(jwt.getAccessToken());

        asyncProcessor.saveUserAsync(dto, keycloakUserId);

        return jwt;
    }

    public JwtAuthenticationResponseDto refreshToken(RefreshTokenRequestDto refreshToken) {
        return keycloakClient.refreshToken(refreshToken);
    }

    public void logout(RefreshTokenRequestDto refreshToken) {
        keycloakClient.logoutUser(refreshToken);
    }

}
