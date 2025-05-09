package com.ecommerce.userservice.user.repository;

import com.ecommerce.userservice.user.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query(value = "SELECT * FROM users u WHERE u.id = :id AND is_active = :isActive", nativeQuery = true)
    Optional<User> findByIdAndIsActive(@Param("id") Long id, @Param("isActive") boolean isActive);

    Optional<User> findByEmail(String email);

    Optional<User> findByKeycloakId(UUID keycloakId);

    @Query("SELECT u.id FROM User u WHERE u.keycloakId = :keycloakId")
    Optional<Long> findUserIdByKeycloakId(@Param("keycloakId") UUID keycloakId);
}
