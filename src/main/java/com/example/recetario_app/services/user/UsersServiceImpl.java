package com.example.recetario_app.services.user;

import com.example.recetario_app.domain.entities.auth.RoleEntity;
import com.example.recetario_app.domain.entities.auth.UserEntity;
import com.example.recetario_app.domain.exception.ResourceNotFoundException;
import com.example.recetario_app.domain.repositories.auth.RoleRepository;
import com.example.recetario_app.domain.repositories.auth.UsersRepository;
import com.example.recetario_app.infrastructure.dtos.user.UserResponse;
import com.example.recetario_app.infrastructure.dtos.user.UsersRequest;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class UsersServiceImpl implements UsersService{

    private final UsersRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private static final Logger logger = LoggerFactory.getLogger(UsersServiceImpl.class);

    @Override
    public List<UserResponse> getAllUsers(){
        logger.info("Buscando todos los users - metodo getAllUserss");
        return userRepository.findAll()
                .stream()
                .map(UserResponse::fromEntity)
                .toList();
    }

    @Override
    public Optional<UserResponse> getUsersById(Long id) {
        logger.info("Buscando Users por ID {} - metodo getUsersById", id);
        return userRepository.findById(id)
                .map(UserResponse::fromEntity);
    }

    @Override
    public UserResponse createUsers(UsersRequest request) {
        logger.info("Creating user with username: {}", request.getUsername());

        userRepository.findByUsername(request.getUsername()).ifPresent(u -> {
            throw new IllegalArgumentException("Username already exists: " + request.getUsername());
        });

        UserEntity toPersist = UserEntity.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .active(request.isActive())
                .build();

        if (request.getRoleId() != null && !request.getRoleId().isEmpty()) {
            List<RoleEntity> roles = roleRepository.findAllById(request.getRoleId());
            toPersist.setRoles(roles);
        }

        UserEntity persisted = userRepository.save(toPersist);
        logger.info("User created successfully with ID: {}", persisted.getId());

        // Retornar DTO limpio
        return UserResponse.fromEntity(persisted);
    }

    @Override
    public UserResponse updateUsers(Long id, UsersRequest updateRequest) {
        logger.info("Updating user with ID: {} and request: {} - method updateUsers", id, updateRequest);

        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + id));

        if (updateRequest.getUsername() != null && !updateRequest.getUsername().isBlank()) {
            logger.info("Updating username to: {} - method updateUsers", updateRequest.getUsername());
            user.setUsername(updateRequest.getUsername());
        }

        if (updateRequest.getPassword() != null && !updateRequest.getPassword().isBlank()) {
            logger.info("Updating password - method updateUsers");
            user.setPassword(passwordEncoder.encode(updateRequest.getPassword()));
        }

        if (updateRequest.getRoleId() != null && !updateRequest.getRoleId().isEmpty()) {
            logger.info("Updating user roles - method updateUsers");
            List<RoleEntity> roles = roleRepository.findAllById(updateRequest.getRoleId());
            user.setRoles(roles);
        }

        UserEntity updatedUser = userRepository.save(user);
        logger.info("User updated successfully. User ID: {}", updatedUser.getId());

        return UserResponse.fromEntity(updatedUser);
    }

    @Override
    public void deleteUsersById(Long id) {
        logger.info("Eliminando Users por ID : {} - metodo deleteUsersById", id);
        Optional<UserEntity> user = userRepository.findById(id);
        if (user.isEmpty()) {
            logger.info("Users id {} no encontrado - metodo deleteUsersById", id);
            throw new ResourceNotFoundException("Users NO ENCONTRADO");
        }
        logger.info("Eliminando Users - metodo deleteUsersById" );
        logger.info("Id Users {} - metodo deleteUsersById" , id);
        userRepository.deleteById(id);
        logger.info("Users eliminado satisfactoriamente - metodo deleteUsersById");

    }
}
