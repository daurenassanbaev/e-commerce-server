package com.ecommerce.userservice.service;

import com.ecommerce.userservice.model.converter.UserConverter;
import com.ecommerce.userservice.model.dto.UserDto;
import com.ecommerce.userservice.model.entity.User;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;

@Service
public class UserCacheService {

    @CachePut(value = "users", key = "#user.id")
    public UserDto putUser(User user) {
        return UserConverter.entityToDto(user);
    }
}
