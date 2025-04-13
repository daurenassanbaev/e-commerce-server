package com.ecommerce.userservice.repository;

import com.ecommerce.userservice.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query(value = "SELECT * FROM users u WHERE u.id = :id AND is_active = :isActive", nativeQuery = true)
    Optional<User> findByIdAndIsActive(Long id, boolean isActive);

    Optional<User> findByEmail(String email);

    Optional<User> findByKeycloakId(UUID keycloakId);
}
