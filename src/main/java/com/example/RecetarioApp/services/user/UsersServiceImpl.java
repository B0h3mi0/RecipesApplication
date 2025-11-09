package com.example.RecetarioApp.services.user;

import com.example.RecetarioApp.domain.entities.RecipeEntity;
import com.example.RecetarioApp.domain.entities.RoleEntity;
import com.example.RecetarioApp.domain.entities.UserEntity;
import com.example.RecetarioApp.domain.exception.DatabaseTransactionException;
import com.example.RecetarioApp.domain.exception.ResourceNotFoundException;
import com.example.RecetarioApp.domain.repositories.RoleRepository;
import com.example.RecetarioApp.domain.repositories.UsersRepository;
import com.example.RecetarioApp.infrastructure.dtos.role.RoleResponse;
import com.example.RecetarioApp.infrastructure.dtos.user.UserResponse;
import com.example.RecetarioApp.infrastructure.dtos.user.UsersRequest;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class UsersServiceImpl implements UsersService{

    private final RoleRepository roleRepository;
    private final UsersRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private static final Logger logger = LoggerFactory.getLogger(UsersServiceImpl.class);

    @Override
    public List<UserResponse> getAllUsers(){
        logger.info("Buscando todos los users - metodo getAllUserss");
        List<UserEntity> users = ( List<UserEntity> ) userRepository.findAll();
        return users.stream()
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

        // (opcional) Validar username duplicado
        userRepository.findByUsername(request.getUsername()).ifPresent(u -> {
            throw new IllegalArgumentException("Username already exists: " + request.getUsername());
        });

        // Construir la entidad para persistir
        UserEntity toPersist = UserEntity.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .active(request.isActive())
                .build();

        // Asociar roles si vienen IDs
        if (request.getRoleId() != null && !request.getRoleId().isEmpty()) {
            List<RoleEntity> roles = roleRepository.findAllById(request.getRoleId());
            toPersist.setRole(roles);
        }

        // Guardar usuario
        UserEntity persisted = userRepository.save(toPersist);
        logger.info("User created successfully with ID: {}", persisted.getId());

        // Retornar DTO limpio
        return UserResponse.fromEntity(persisted);
    }

    @Override
    public UserResponse updateUsers(Long id, UsersRequest updateRequest) {
        logger.info("Updating user with ID: {} and request: {} - method updateUsers", id, updateRequest);

        // Buscar usuario
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + id));

        // Actualizar solo los campos enviados
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
            user.setRole(roles);
        }

        // Guardar los cambios
        UserEntity updatedUser = userRepository.save(user);
        logger.info("User updated successfully. User ID: {}", updatedUser.getId());

        return UserResponse.fromEntity(updatedUser);
    }

        @Override
        public void deleteUsersById(Long id) {
            logger.info("Deleting user by ID: {} - method deleteById", id);

            UserEntity users = userRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + id));

            userRepository.delete(users);
            logger.info("User successfully deleted. ID: {}", id);
        }
}
