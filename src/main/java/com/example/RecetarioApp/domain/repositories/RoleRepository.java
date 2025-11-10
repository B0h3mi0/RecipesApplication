package com.example.RecetarioApp.domain.repositories;

import com.example.RecetarioApp.domain.entities.RoleEntity;
import com.example.RecetarioApp.domain.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<RoleEntity, Long> {
    Optional<RoleEntity> findByRoleName(String roleName);
}
