package com.example.RecetarioApp.controller;

import com.example.RecetarioApp.domain.UsersEntity;
import com.example.RecetarioApp.request.UsersUpdateRequest;
import com.example.RecetarioApp.service.UsersService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private UsersService userService;

    @GetMapping("/all")
    public ResponseEntity<List<UsersEntity>> getAllUsers() {
        logger.info("Fetching all users...");
        List<UsersEntity> users = userService.getAllUsers();
        logger.info("Successfully retrieved {} users.", users.size());
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsersEntity> getUsersById(@PathVariable Long id) {
        logger.info("Fetching user by ID: {}", id);
        Optional<UsersEntity> user = userService.getUsersById(id);
        return user.map(value -> {
                    logger.info("User found with ID: {}", id);
                    return new ResponseEntity<>(value, HttpStatus.OK);
                })
                .orElseGet(() -> {
                    logger.warn("User not found with ID: {}", id);
                    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                });
    }

    @PostMapping
    public ResponseEntity<UsersEntity> createUser(@RequestBody UsersEntity user) {
        logger.info("Creating a new user with request: {}", user);
        UsersEntity savedUser = userService.createUsers(user);
        logger.info("User successfully created. User ID: {}", savedUser.getId());
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<UsersEntity> updateUser(@PathVariable Long id, @RequestBody UsersUpdateRequest updateRequest) {
        logger.info("Updating user with ID: {} and request: {}", id, updateRequest);
        UsersEntity updatedUser = userService.updateUsers(id, updateRequest);
        logger.info("User successfully updated. User ID: {}", updatedUser.getId());
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        logger.info("Deleting user with ID: {}", id);
        userService.deleteUsersById(id);
        logger.info("User successfully deleted. User ID: {}", id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
