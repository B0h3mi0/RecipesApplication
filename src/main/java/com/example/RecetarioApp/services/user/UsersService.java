package com.example.RecetarioApp.services.user;

import com.example.RecetarioApp.infrastructure.dtos.user.UserResponse;
import com.example.RecetarioApp.infrastructure.dtos.user.UsersRequest;

import java.util.List;
import java.util.Optional;

public interface UsersService {
    List<UserResponse> getAllUsers();
    Optional<UserResponse> getUsersById(Long id);
    UserResponse createUsers(UsersRequest user);
    UserResponse updateUsers(Long id, UsersRequest updateRequest);
    void deleteUsersById (Long id);
}
