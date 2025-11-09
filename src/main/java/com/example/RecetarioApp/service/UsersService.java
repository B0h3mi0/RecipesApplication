package com.example.RecetarioApp.service;

import com.example.RecetarioApp.domain.UsersEntity;
import com.example.RecetarioApp.request.UsersUpdateRequest;

import java.util.List;
import java.util.Optional;

public interface UsersService {
    List<UsersEntity> getAllUsers();
    Optional<UsersEntity> getUsersById(Long id);
    UsersEntity createUsers(UsersEntity user);
    UsersEntity updateUsers(Long id, UsersUpdateRequest updateRequest);
    void deleteUsersById (Long id);
}
