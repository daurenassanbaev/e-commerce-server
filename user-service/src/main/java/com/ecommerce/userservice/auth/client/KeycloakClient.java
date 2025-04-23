package com.ecommerce.userservice.auth.client;

import com.ecommerce.userservice.auth.config.SecurityProperties;
import com.ecommerce.userservice.auth.exception.LogoutException;
import com.ecommerce.userservice.auth.exception.TokenRefreshException;
import com.ecommerce.userservice.auth.exception.LoginException;
import com.ecommerce.userservice.auth.exception.RegistrationException;
import com.ecommerce.userservice.auth.model.dto.request.LoginRequestDto;
import com.ecommerce.userservice.auth.model.dto.request.RefreshTokenRequestDto;
import com.ecommerce.userservice.auth.model.dto.request.RegistrationRequestDto;
import com.ecommerce.userservice.user.model.dto.request.UpdateUserRequestDto;
import com.ecommerce.userservice.auth.model.dto.response.AdminTokenResponseDto;
import com.ecommerce.userservice.auth.model.dto.response.JwtAuthenticationResponseDto;
import com.ecommerce.userservice.auth.model.dto.response.KeycloakRoleResponseDto;
import com.ecommerce.userservice.user.model.enums.UserRole;
import com.ecommerce.userservice.auth.util.KeycloakAdminTokenProvider;
import com.ecommerce.userservice.auth.util.KeycloakRoleProvider;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Getter
@Component
@RequiredArgsConstructor
public class KeycloakClient {

    private final WebClient keycloakWebClient;
    private final SecurityProperties securityProperties;
    private final KeycloakAdminTokenProvider keycloakAdminTokenProvider;
    private final KeycloakRoleProvider keycloakRoleProvider;

    public JwtAuthenticationResponseDto createUser(RegistrationRequestDto dto) {
        // Получаем access token от АДМИНА
        String accessToken = keycloakAdminTokenProvider.getAdminAccessToken().getAccessToken();

        // Формирование payload
        Map<String, Object> userPayload = Map.of(
                "email", dto.getEmail(),
                "firstName", dto.getFirstName(),
                "lastName", dto.getLastName(),
                "enabled", true,
                "emailVerified", true,
                "attributes", Map.of("phoneNumber", Collections.singletonList(dto.getPhoneNumber())),
                "credentials", Collections.singletonList(
                        Map.of("type", "password", "value", dto.getPassword(), "temporary", false)
                )
        );

        // Запрос на создание User
        String userId = keycloakWebClient.post()
                .uri(securityProperties.getBaseUrl() + "/admin/realms/" + securityProperties.getRealm() + "/users")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(userPayload)
                .exchangeToMono(response -> {
                   if (response.statusCode().is2xxSuccessful()) {
                       String locationHeader = response.headers().asHttpHeaders().getFirst(HttpHeaders.LOCATION);
                       if (locationHeader != null && locationHeader.contains("/users/")) {
                           String extractedUserId = locationHeader.substring(locationHeader.lastIndexOf("/") + 1);
                           return Mono.just(extractedUserId);
                       }
                   }
                   return Mono.error(new RegistrationException("User creation failed or Location header missing"));
                })
                .block();

        // Получение роли
        KeycloakRoleResponseDto keycloakRoleResponseDto = keycloakRoleProvider.getRole(UserRole.USER);

        // Присваивание роли
        keycloakRoleProvider.assignRole(keycloakRoleResponseDto, userId, accessToken);

        // Логин, чтобы сразу получить jwt tokens
        return loginUser(new LoginRequestDto(dto.getEmail(), dto.getPassword()));
    }

    public JwtAuthenticationResponseDto loginUser(LoginRequestDto loginRequestDto) {
        return keycloakWebClient.post()
                .uri("/realms/" + securityProperties.getRealm() + "/protocol/openid-connect/token")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(BodyInserters
                        .fromFormData("grant_type", "password")
                        .with("client_id", securityProperties.getClientUser().getId())
                        .with("client_secret", securityProperties.getClientUser().getSecret())
                        .with("username", loginRequestDto.getEmail())
                        .with("password", loginRequestDto.getPassword())
                )
                .retrieve()
                .onStatus(
                        status -> status.is4xxClientError() || status.is5xxServerError(),
                        response -> response.bodyToMono(String.class)
                                .map(body -> new LoginException(
                                        "Keycloak login failed: " + response.statusCode() + " - " + body))
                )
                .bodyToMono(JwtAuthenticationResponseDto.class)
                .block();
    }

    public JwtAuthenticationResponseDto refreshToken(RefreshTokenRequestDto refreshTokenRequestDto) {
        return keycloakWebClient.post()
                .uri("/realms/" + securityProperties.getRealm() + "/protocol/openid-connect/token")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(BodyInserters
                        .fromFormData("grant_type", "refresh_token")
                        .with("client_id", securityProperties.getClientUser().getId())
                        .with("client_secret", securityProperties.getClientUser().getSecret())
                        .with("refresh_token", refreshTokenRequestDto.getRefreshToken())
                )
                .retrieve()
                .onStatus(
                        status -> status.is4xxClientError() || status.is5xxServerError(),
                        response -> response.bodyToMono(String.class)
                                .map(body -> new TokenRefreshException(
                                        "Keycloak token refresh failed: " + response.statusCode() + " - " + body))
                )
                .bodyToMono(JwtAuthenticationResponseDto.class)
                .block();
    }

    public void logoutUser(RefreshTokenRequestDto refreshTokenRequestDto) {
        keycloakWebClient.post()
                .uri("/realms/" + securityProperties.getRealm() + "/protocol/openid-connect/logout")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(BodyInserters
                        .fromFormData("client_id", securityProperties.getClientUser().getId())
                        .with("client_secret", securityProperties.getClientUser().getSecret())
                        .with("refresh_token", refreshTokenRequestDto.getRefreshToken())
                )
                .retrieve()
                .onStatus(
                        status -> status.is4xxClientError() || status.is5xxServerError(),
                        response -> response.bodyToMono(String.class)
                                .map(body -> new LogoutException(
                                        "Keycloak logout failed: " + response.statusCode() + " - " + body))
                )
                .toBodilessEntity()
                .block();
    }

    public void updateUser(UUID keycloakId, UpdateUserRequestDto dto) {
        AdminTokenResponseDto tokenResponseDto = keycloakAdminTokenProvider.getAdminAccessToken();
        Map<String, Object> payload = Map.of(
                "firstName", dto.getFirstName(),
                "lastName", dto.getLastName(),
                "email", dto.getEmail(),
                "emailVerified", true,
                "attributes", Map.of("phoneNumber", List.of(dto.getPhoneNumber()))
        );

        keycloakWebClient.put()
                .uri("/admin/realms/" + securityProperties.getRealm() + "/users/" + keycloakId)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + tokenResponseDto.getAccessToken())
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(payload)
                .retrieve()
                .onStatus(
                        status -> status.is4xxClientError() || status.is5xxServerError(),
                        response -> response.bodyToMono(String.class)
                                .map(body -> new RegistrationException("Keycloak update failed: " + response.statusCode() + " - " + body))
                )
                .toBodilessEntity()
                .block();
    }

    public void deactivateUser(UUID keycloakId) {
        Map<String, Object> payload = Map.of("enabled", false);

        keycloakWebClient.put()
                .uri("/admin/realms/" + securityProperties.getRealm() + "/users/" + keycloakId)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + keycloakAdminTokenProvider.getAdminAccessToken().getAccessToken())
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(payload)
                .retrieve()
                .onStatus(
                        status -> status.is4xxClientError() || status.is5xxServerError(),
                        response -> response.bodyToMono(String.class)
                                .map(body -> new RegistrationException("Keycloak deactivation failed: " + response.statusCode() + " - " + body))
                )
                .toBodilessEntity()
                .block();
    }

    public void activateUser(UUID keycloakId) {
        Map<String, Object> payload = Map.of("enabled", true);

        keycloakWebClient.put()
                .uri("/admin/realms/" + securityProperties.getRealm() + "/users/" + keycloakId)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + keycloakAdminTokenProvider.getAdminAccessToken().getAccessToken())
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(payload)
                .retrieve()
                .onStatus(
                        status -> status.is4xxClientError() || status.is5xxServerError(),
                        response -> response.bodyToMono(String.class)
                                .map(body -> new RegistrationException("Keycloak activation failed: " + response.statusCode() + " - " + body))
                )
                .toBodilessEntity()
                .block();
    }
}
