package com.example.recetario_app.domain.repositories.auth;

import com.example.recetario_app.domain.entities.auth.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsersRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByUsername(String username);
}
