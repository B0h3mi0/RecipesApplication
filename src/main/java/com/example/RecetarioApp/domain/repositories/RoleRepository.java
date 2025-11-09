package com.example.RecetarioApp.domain.repositories;

import com.example.RecetarioApp.domain.entities.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<RoleEntity, Long> {
}
