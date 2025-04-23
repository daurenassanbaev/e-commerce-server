package com.ecommerce.userservice.auth.util;

import com.ecommerce.userservice.auth.config.SecurityProperties;
import com.ecommerce.userservice.auth.exception.AdminTokenException;
import com.ecommerce.userservice.auth.model.dto.response.AdminTokenResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@RequiredArgsConstructor
public class KeycloakAdminTokenProvider {

    private final WebClient webClient;
    private final SecurityProperties securityProperties;

    public AdminTokenResponseDto getAdminAccessToken() {
        String uri = "/realms/" + securityProperties.getRealm() + "/protocol/openid-connect/token";
        return webClient.post()
                .uri(uri)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(BodyInserters
                        .fromFormData("grant_type", "client_credentials")
                        .with("client_id", securityProperties.getClientAdmin().getId())
                        .with("client_secret", securityProperties.getClientAdmin().getSecret())
                )
                .retrieve()
                .onStatus(
                        status -> status.is4xxClientError() || status.is5xxServerError(),
                        response -> response.bodyToMono(String.class)
                                .map(body -> new AdminTokenException(
                                        "Failed to get admin token: " + response.statusCode() + " - " + body))
                )
                .bodyToMono(AdminTokenResponseDto.class)
                .block();
    }
}
