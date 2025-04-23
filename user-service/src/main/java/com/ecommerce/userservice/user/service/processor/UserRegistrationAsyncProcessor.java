package com.ecommerce.userservice.user.service.processor;

import com.ecommerce.userservice.auth.model.dto.request.RegistrationRequestDto;
import com.ecommerce.userservice.user.model.entity.User;
import com.ecommerce.userservice.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserRegistrationAsyncProcessor {
    private final UserService userService;

    @Async
    @Transactional
    public void saveUserAsync(RegistrationRequestDto dto, UUID keycloakId) {
        User user = new User();
        user.setEmail(dto.getEmail());
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setPhoneNumber(dto.getPhoneNumber());
        user.setActive(true);
        user.setKeycloakId(keycloakId);
        user.setCreatedAt(LocalDateTime.now());

        userService.save(user);
    }
}
