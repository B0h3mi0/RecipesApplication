package com.example.RecetarioApp.services.role;

import com.example.RecetarioApp.domain.entities.auth.RoleEntity;

import java.util.List;
import java.util.Optional;

public interface RoleService {

    List<RoleEntity> getAllRoles();
    Optional<RoleEntity> getRoleById(Long id);
}
