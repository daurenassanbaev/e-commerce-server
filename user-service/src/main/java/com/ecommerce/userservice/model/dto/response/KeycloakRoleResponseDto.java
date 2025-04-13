package com.ecommerce.userservice.model.dto.response;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class KeycloakRoleResponseDto {
    private String id;
    private String name;
}