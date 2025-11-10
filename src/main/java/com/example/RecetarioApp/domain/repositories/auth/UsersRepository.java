package com.example.RecetarioApp.domain.repositories.auth;

import com.example.RecetarioApp.domain.entities.auth.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsersRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByUsername(String username);
}
