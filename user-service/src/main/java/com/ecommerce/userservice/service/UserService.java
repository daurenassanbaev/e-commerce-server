package com.ecommerce.userservice.service;

import com.ecommerce.userservice.client.KeycloakClient;
import com.ecommerce.userservice.exception.AccessDeniedException;
import com.ecommerce.userservice.exception.ResourceNotFoundException;
import com.ecommerce.userservice.exception.UserIsAlreadyActivatedException;
import com.ecommerce.userservice.exception.UserIsAlreadyArchivedException;
import com.ecommerce.userservice.model.converter.UserConverter;
import com.ecommerce.userservice.model.dto.request.UpdateUserRequestDto;
import com.ecommerce.userservice.model.dto.UserDto;
import com.ecommerce.userservice.model.entity.User;
import com.ecommerce.userservice.model.enums.UserRole;
import com.ecommerce.userservice.repository.UserRepository;
import com.ecommerce.userservice.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final KeycloakClient keycloakClient;

    @Transactional
    public void save(User user) {
        userRepository.save(user);
    }

    public UserDto getById(Long id) {
        return UserConverter.entityToDto(findById(id, true));
    }

    public Optional<User> getByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Transactional
    public UserDto updateMe(UpdateUserRequestDto dto, String token) {
        UUID callerId = JwtUtil.extractSubject(token);
        User user = userRepository.findByKeycloakId(callerId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "KeycloakId", callerId.toString()));

        user.setEmail(dto.getEmail());
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setPhoneNumber(dto.getPhoneNumber());
        user.setUpdatedAt(LocalDateTime.now());

        userRepository.save(user);
        keycloakClient.updateUser(user.getKeycloakId(), dto);

        return UserConverter.entityToDto(user);
    }

    @Transactional
    public UserDto updateByAdmin(Long id, UpdateUserRequestDto dto) {
        User user = findById(id, true);

        user.setEmail(dto.getEmail());
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setPhoneNumber(dto.getPhoneNumber());
        user.setUpdatedAt(LocalDateTime.now());

        userRepository.save(user);
        keycloakClient.updateUser(user.getKeycloakId(), dto);

        return UserConverter.entityToDto(user);
    }

    @Transactional
    public UserDto archive(Long id) {
        User user = findById(id, true);

        if (!user.isActive()) {
            throw new UserIsAlreadyArchivedException("User with ID %d is already archived".formatted(id));
        }

        user.setActive(false);
        user.setUpdatedAt(LocalDateTime.now());
        keycloakClient.deactivateUser(user.getKeycloakId());
        return UserConverter.entityToDto(userRepository.save(user));
    }

    @Transactional
    public UserDto activate(Long id) {
        User user = findById(id, false);

        if (user.isActive()) {
            throw new UserIsAlreadyActivatedException("User with ID %d is already activated".formatted(id));
        }

        user.setActive(true);
        user.setUpdatedAt(LocalDateTime.now());
        keycloakClient.activateUser(user.getKeycloakId());
        return UserConverter.entityToDto(userRepository.save(user));
    }

    private User findById(Long id, boolean isActive) {
        return userRepository.findByIdAndIsActive(id, isActive).orElseThrow(
                () -> new ResourceNotFoundException("User", "UserId", id.toString())
        );
    }

    public UserDto getMe(String token) {
        UUID keycloakId = JwtUtil.extractSubject(token);
        User user = userRepository.findByKeycloakId(keycloakId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "KeycloakId", keycloakId.toString()));
        return UserConverter.entityToDto(user);
    }
}
