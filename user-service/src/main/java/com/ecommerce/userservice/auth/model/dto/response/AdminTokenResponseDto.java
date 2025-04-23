package com.ecommerce.userservice.auth.model.dto.response;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdminTokenResponseDto {
    @JsonProperty("access_token")
    private String accessToken;
}
