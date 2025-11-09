package com.example.RecetarioApp.repository;

import com.example.RecetarioApp.domain.UsersEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<UsersEntity, Long> {
}
