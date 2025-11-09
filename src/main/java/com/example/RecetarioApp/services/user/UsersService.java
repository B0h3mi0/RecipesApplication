package com.example.RecetarioApp.services.user;

import com.example.RecetarioApp.domain.entities.UserEntity;
import com.example.RecetarioApp.infrastructure.dtos.user.UsersUpdateRequest;

import java.util.List;
import java.util.Optional;

public interface UsersService {
    List<UserEntity> getAllUsers();
    Optional<UserEntity> getUsersById(Long id);
    UserEntity createUsers(UserEntity user);
    UserEntity updateUsers(Long id, UsersUpdateRequest updateRequest);
    void deleteUsersById (Long id);
}
