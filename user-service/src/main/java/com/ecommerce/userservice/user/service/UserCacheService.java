package com.ecommerce.userservice.user.service;

import com.ecommerce.userservice.user.model.converter.UserConverter;
import com.ecommerce.userservice.user.model.dto.UserDto;
import com.ecommerce.userservice.user.model.entity.User;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;

@Service
public class UserCacheService {

    @CachePut(value = "users", key = "#user.id")
    public UserDto putUser(User user) {
        return UserConverter.entityToDto(user);
    }
}
