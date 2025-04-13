package com.ecommerce.userservice.model.converter;

import com.ecommerce.userservice.model.dto.UserDto;
import com.ecommerce.userservice.model.entity.User;

public class UserConverter {

    public static UserDto entityToDto(User user) {
        UserDto dto = new UserDto();

        dto.setId(user.getId());
        dto.setEmail(user.getEmail());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setPhoneNumber(user.getPhoneNumber());
        dto.setCreatedAt(user.getCreatedAt());
        dto.setUpdatedAt(user.getUpdatedAt());

        return dto;
    }
}
