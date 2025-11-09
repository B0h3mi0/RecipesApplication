package com.example.RecetarioApp.service;

import com.example.RecetarioApp.domain.RoleEntity;

import java.util.List;
import java.util.Optional;

public interface RoleService {

    List<RoleEntity> getAllRoles();
    Optional<RoleEntity> getRoleById(Long id);
}
