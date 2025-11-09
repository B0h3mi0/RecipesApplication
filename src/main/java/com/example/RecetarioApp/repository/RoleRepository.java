package com.example.RecetarioApp.repository;

import com.example.RecetarioApp.domain.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<RoleEntity, Long> {
}
