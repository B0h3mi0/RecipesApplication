package com.example.RecetarioApp.domain.repositories;

import com.example.RecetarioApp.domain.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<UserEntity, Long> {
}
