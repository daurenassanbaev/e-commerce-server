package com.ecommerce.userservice.user.service;

import com.ecommerce.common.exception.AlreadyActivatedException;
import com.ecommerce.common.exception.AlreadyArchivedException;
import com.ecommerce.common.exception.ResourceNotFoundException;
import com.ecommerce.userservice.auth.client.KeycloakClient;
import com.ecommerce.userservice.user.model.converter.UserConverter;
import com.ecommerce.userservice.user.model.dto.request.UpdateUserRequestDto;
import com.ecommerce.userservice.user.model.dto.UserDto;
import com.ecommerce.userservice.user.model.entity.User;
import com.ecommerce.userservice.user.repository.UserRepository;
import com.ecommerce.userservice.auth.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final KeycloakClient keycloakClient;
    private final UserCacheService userCacheService;;

    @Transactional
    public void save(User user) {
        userRepository.save(user);
    }

    @Cacheable(value = "users", key = "#id")
    public UserDto getById(Long id) {
        return UserConverter.entityToDto(findById(id, true));
    }

    public UserDto getMe(String token) {
        UUID keycloakId = JwtUtil.extractSubject(token);
        User user = getUserByKeycloakId(keycloakId);
        return UserConverter.entityToDto(user);
    }

    @Transactional
    public UserDto updateMe(UpdateUserRequestDto dto, String token) {
        UUID callerId = JwtUtil.extractSubject(token);
        User user = getUserByKeycloakId(callerId);
        updateUser(user, dto);
        return userCacheService.putUser(user);
    }

    @Transactional
    public UserDto updateByAdmin(Long id, UpdateUserRequestDto dto) {
        User user = findById(id, true);
        updateUser(user, dto);
        return userCacheService.putUser(user);
    }

    @Transactional
    @CacheEvict(value = "users", key = "#id")
    public UserDto archive(Long id) {
        User user = findById(id, true);
        if (!user.isActive()) {
            throw new AlreadyArchivedException("User with ID %d is already archived".formatted(id));
        }
        updateUserStatus(user, false);
        keycloakClient.deactivateUser(user.getKeycloakId());
        return UserConverter.entityToDto(user);
    }

    @Transactional
    @CachePut(value = "users", key = "#id")
    public UserDto activate(Long id) {
        User user = findById(id, false);
        if (user.isActive()) {
            throw new AlreadyActivatedException("User with ID %d is already activated".formatted(id));
        }
        updateUserStatus(user, true);
        keycloakClient.activateUser(user.getKeycloakId());
        return UserConverter.entityToDto(user);
    }

    private User findById(Long id, boolean isActive) {
        return userRepository.findByIdAndIsActive(id, isActive)
                .orElseThrow(() -> new ResourceNotFoundException("User", "UserId", id.toString()));
    }

    private User getUserByKeycloakId(UUID keycloakId) {
        return userRepository.findByKeycloakId(keycloakId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "KeycloakId", keycloakId.toString()));
    }

    private void updateUser(User user, UpdateUserRequestDto dto) {
        user.setEmail(dto.getEmail());
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setPhoneNumber(dto.getPhoneNumber());
        user.setUpdatedAt(LocalDateTime.now());

        userRepository.save(user);
        keycloakClient.updateUser(user.getKeycloakId(), dto);
    }

    private void updateUserStatus(User user, boolean isActive) {
        user.setActive(isActive);
        user.setUpdatedAt(LocalDateTime.now());
        userRepository.save(user);
    }
}