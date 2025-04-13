package com.ecommerce.userservice.model.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegistrationRequestDto {
    private String email;
    private String firstName;
    private String lastName;
    private String password;
    private String phoneNumber;
}
