package com.example.recetario_app.services.role;

import com.example.recetario_app.domain.entities.auth.RoleEntity;

import java.util.List;
import java.util.Optional;

public interface RoleService {

    List<RoleEntity> getAllRoles();
    Optional<RoleEntity> getRoleById(Long id);
}
