package com.ecommerce.userservice.user.controller;

import com.ecommerce.userservice.user.model.dto.request.UpdateUserRequestDto;
import com.ecommerce.userservice.user.model.dto.UserDto;
import com.ecommerce.userservice.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Validated
public class UserController {

    private final UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getById(id));
    }

    @GetMapping("/me")
    public ResponseEntity<UserDto> getCurrentUser(@RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(userService.getMe(token));
    }

    @PutMapping("/me")
    public ResponseEntity<UserDto> updateMe(
            @RequestBody UpdateUserRequestDto requestDto,
            @RequestHeader("Authorization") String token
    ) {
        return ResponseEntity.ok(userService.updateMe(requestDto, token));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateAnyUser(
            @PathVariable Long id,
            @RequestBody UpdateUserRequestDto requestDto
    ) {
        return ResponseEntity.ok(userService.updateByAdmin(id, requestDto));
    }

    @PatchMapping("/{id}/archive")
    public ResponseEntity<UserDto> archive(@PathVariable Long id) {
        return ResponseEntity.ok(userService.archive(id));
    }

    @PatchMapping("/{id}/activate")
    public ResponseEntity<UserDto> activate(@PathVariable Long id) {
        return ResponseEntity.ok(userService.activate(id));
    }

    @GetMapping("/internal/keycloak/{keycloakId}")
    public ResponseEntity<Long> getUserIdByKeycloakId(@PathVariable("keycloakId") UUID keycloakId) {
        return ResponseEntity.ok(userService.getUserIdByKeycloakId(keycloakId));
    }

    @GetMapping("/internal/{userId}/active")
    public ResponseEntity<Boolean> isUserActive(Long userId) {
        return ResponseEntity.ok(userService.isUserActive(userId));
    }
}
