package com.example.RecetarioApp.domain.repositories.auth;

import com.example.RecetarioApp.domain.entities.auth.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<RoleEntity, Long> {
    Optional<RoleEntity> findByName(String name);
}
