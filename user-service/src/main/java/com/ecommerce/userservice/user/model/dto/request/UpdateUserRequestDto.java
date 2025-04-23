package com.ecommerce.userservice.user.model.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateUserRequestDto {

    private String email;
    private String firstName;
    private String lastName;
    private String phoneNumber;

}
