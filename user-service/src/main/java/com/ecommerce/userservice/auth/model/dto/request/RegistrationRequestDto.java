package com.ecommerce.userservice.auth.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationRequestDto {
    private String email;
    private String firstName;
    private String lastName;
    private String password;
    private String phoneNumber;
}
