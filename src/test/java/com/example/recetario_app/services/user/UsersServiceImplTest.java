package com.example.recetario_app.services.user;

import com.example.recetario_app.domain.entities.auth.RoleEntity;
import com.example.recetario_app.domain.entities.auth.UserEntity;
import com.example.recetario_app.domain.exception.ResourceNotFoundException;
import com.example.recetario_app.domain.repositories.auth.RoleRepository;
import com.example.recetario_app.domain.repositories.auth.UsersRepository;
import com.example.recetario_app.infrastructure.dtos.user.UserResponse;
import com.example.recetario_app.infrastructure.dtos.user.UsersRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UsersServiceImplTest {

    @Mock
    private UsersRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private UsersServiceImpl usersService;

    @Test
    void getAllUsers_ShouldReturnListOfUserResponses() {
        UserEntity user1 = UserEntity.builder().id(1L).username("user1").roles(new ArrayList<>()).build();
        UserEntity user2 = UserEntity.builder().id(2L).username("user2").roles(new ArrayList<>()).build();
        when(userRepository.findAll()).thenReturn(Arrays.asList(user1, user2));

        List<UserResponse> result = usersService.getAllUsers();

        assertEquals(2, result.size());
        assertEquals("user1", result.get(0).getUsername());
        assertEquals("user2", result.get(1).getUsername());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void getUsersById_ShouldReturnUserResponse_WhenIdExists() {
        Long id = 1L;
        UserEntity user = UserEntity.builder().id(id).username("user1").roles(new ArrayList<>()).build();
        when(userRepository.findById(id)).thenReturn(Optional.of(user));

        Optional<UserResponse> result = usersService.getUsersById(id);

        assertTrue(result.isPresent());
        assertEquals("user1", result.get().getUsername());
        verify(userRepository, times(1)).findById(id);
    }

    @Test
    void getUsersById_ShouldReturnEmpty_WhenIdDoesNotExist() {
        Long id = 1L;
        when(userRepository.findById(id)).thenReturn(Optional.empty());

        Optional<UserResponse> result = usersService.getUsersById(id);

        assertFalse(result.isPresent());
        verify(userRepository, times(1)).findById(id);
    }

    @Test
    void createUsers_ShouldReturnUserResponse_WhenDataIsValid() {
        UsersRequest request = new UsersRequest();
        request.setUsername("newUser");
        request.setPassword("password");
        request.setRoleId(Collections.singletonList(1L));

        UserEntity savedUser = UserEntity.builder().id(1L).username("newUser").roles(new ArrayList<>()).build();
        RoleEntity role = new RoleEntity();
        role.setId(1L);

        when(userRepository.findByUsername("newUser")).thenReturn(Optional.empty());
        when(passwordEncoder.encode("password")).thenReturn("encodedPassword");
        when(roleRepository.findAllById(anyList())).thenReturn(Collections.singletonList(role));
        when(userRepository.save(any(UserEntity.class))).thenReturn(savedUser);

        UserResponse result = usersService.createUsers(request);

        assertNotNull(result);
        assertEquals("newUser", result.getUsername());
        verify(userRepository, times(1)).findByUsername("newUser");
        verify(passwordEncoder, times(1)).encode("password");
        verify(roleRepository, times(1)).findAllById(anyList());
        verify(userRepository, times(1)).save(any(UserEntity.class));
    }

    @Test
    void createUsers_ShouldThrowException_WhenUsernameAlreadyExists() {
        UsersRequest request = new UsersRequest();
        request.setUsername("existingUser");

        when(userRepository.findByUsername("existingUser")).thenReturn(Optional.of(new UserEntity()));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> usersService.createUsers(request));
        assertEquals("Username already exists: existingUser", exception.getMessage());
        verify(userRepository, times(1)).findByUsername("existingUser");
        verify(userRepository, never()).save(any(UserEntity.class));
    }

    @Test
    void updateUsers_ShouldReturnUpdatedResponse_WhenIdExists() {
        Long id = 1L;
        UsersRequest request = new UsersRequest();
        request.setUsername("updatedUser");
        request.setPassword("newPassword");
        request.setRoleId(Collections.singletonList(1L));

        UserEntity existingUser = UserEntity.builder().id(id).username("oldUser").roles(new ArrayList<>()).build();
        UserEntity updatedUser = UserEntity.builder().id(id).username("updatedUser").roles(new ArrayList<>()).build();
        RoleEntity role = new RoleEntity();
        role.setId(1L);

        when(userRepository.findById(id)).thenReturn(Optional.of(existingUser));
        when(passwordEncoder.encode("newPassword")).thenReturn("encodedNewPassword");
        when(roleRepository.findAllById(anyList())).thenReturn(Collections.singletonList(role));
        when(userRepository.save(any(UserEntity.class))).thenReturn(updatedUser);

        UserResponse result = usersService.updateUsers(id, request);

        assertEquals("updatedUser", result.getUsername());
        verify(userRepository, times(1)).findById(id);
        verify(passwordEncoder, times(1)).encode("newPassword");
        verify(roleRepository, times(1)).findAllById(anyList());
        verify(userRepository, times(1)).save(existingUser);
    }

    @Test
    void updateUsers_ShouldThrowException_WhenIdDoesNotExist() {
        Long id = 1L;
        UsersRequest request = new UsersRequest();

        when(userRepository.findById(id)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
                () -> usersService.updateUsers(id, request));
        assertEquals("User not found with ID: " + id, exception.getMessage());
        verify(userRepository, times(1)).findById(id);
        verify(userRepository, never()).save(any(UserEntity.class));
    }

    @Test
    void deleteUsersById_ShouldDelete_WhenIdExists() {
        Long id = 1L;
        UserEntity user = UserEntity.builder().id(id).build();
        when(userRepository.findById(id)).thenReturn(Optional.of(user));

        usersService.deleteUsersById(id);

        verify(userRepository, times(1)).findById(id);
        verify(userRepository, times(1)).deleteById(id);
    }

    @Test
    void deleteUsersById_ShouldThrowException_WhenIdDoesNotExist() {
        Long id = 1L;
        when(userRepository.findById(id)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
                () -> usersService.deleteUsersById(id));
        assertEquals("Users NO ENCONTRADO", exception.getMessage());
        verify(userRepository, times(1)).findById(id);
        verify(userRepository, never()).deleteById(id);
    }

    @Test
    void updateUsers_ShouldUpdateAllFields_WhenAllFieldsPresent() {
        Long userId = 1L;

        UsersRequest request = new UsersRequest();
        request.setUsername("newUser");
        request.setPassword("newPass");
        request.setRoleId(List.of(1L));

        UserEntity user = new UserEntity();
        user.setId(userId);

        RoleEntity role = new RoleEntity();

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(passwordEncoder.encode("newPass")).thenReturn("encodedPass");
        when(roleRepository.findAllById(request.getRoleId())).thenReturn(List.of(role));
        when(userRepository.save(any(UserEntity.class))).thenAnswer(inv -> inv.getArgument(0));

        usersService.updateUsers(userId, request);

        verify(passwordEncoder).encode("newPass");
        verify(roleRepository).findAllById(request.getRoleId());
        verify(userRepository).save(user);

        assertEquals("newUser", user.getUsername());
        assertEquals("encodedPass", user.getPassword());
        assertEquals(List.of(role), user.getRoles());
    }

    @Test
    void updateUsers_ShouldNotUpdateAnyField_WhenAllFieldsNullOrEmpty() {
        Long userId = 1L;

        UsersRequest request = new UsersRequest();
        request.setUsername(" ");
        request.setPassword(null);
        request.setRoleId(Collections.emptyList());

        UserEntity user = new UserEntity();
        user.setId(userId);
        user.setRoles(Collections.emptyList());
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(userRepository.save(any(UserEntity.class))).thenAnswer(inv -> inv.getArgument(0));

        usersService.updateUsers(userId, request);

        verify(passwordEncoder, never()).encode(any());
        verify(userRepository).save(user);
    }

    @Test
    void updateUsers_ShouldOnlyUpdatePassword_WhenUsernameAndRolesAreNull() {
        Long userId = 1L;

        UsersRequest request = new UsersRequest();
        request.setUsername(null);
        request.setPassword("newPass");
        request.setRoleId(null);

        UserEntity user = new UserEntity();
        user.setId(userId);
        user.setUsername("oldUser");
        user.setPassword("oldPass");
        user.setRoles(Collections.singletonList(new RoleEntity()));

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(passwordEncoder.encode("newPass")).thenReturn("encodedPass");
        when(userRepository.save(any(UserEntity.class))).thenAnswer(inv -> inv.getArgument(0));

        UserResponse response = usersService.updateUsers(userId, request);

        assertEquals("oldUser", user.getUsername());
        assertEquals("encodedPass", user.getPassword());
        assertNotNull(user.getRoles());
        assertEquals(1, user.getRoles().size());

        verify(passwordEncoder).encode("newPass");
        verify(roleRepository, never()).findAllById(any());
        verify(userRepository).save(user);
    }

    @Test
    void createUsers_ShouldNotSetRoles_WhenRoleIdIsNull() {
        UsersRequest request = new UsersRequest();
        request.setUsername("newUser");
        request.setRoleId(null);
        request.setActive(true);

        when(userRepository.findByUsername("newUser")).thenReturn(Optional.empty());
        when(userRepository.save(any(UserEntity.class))).thenAnswer(inv -> inv.getArgument(0));

        UserResponse response = usersService.createUsers(request);

        assertEquals("newUser", response.getUsername());
        assertTrue(response.isActive());
        assertEquals(List.of(),response.getRoles());

        verify(roleRepository, never()).findAllById(any());
        verify(userRepository).save(any(UserEntity.class));
    }


}
