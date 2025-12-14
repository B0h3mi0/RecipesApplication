package com.example.recetario_app.security;

import com.example.recetario_app.domain.entities.auth.UserEntity;
import com.example.recetario_app.domain.repositories.auth.UsersRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class JpaUserDetailServiceTest {

    @Mock
    private UsersRepository repository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private JpaUserDetailService userDetailsService;

    @Test
    void loadUserByUsername_ShouldReturnUserDetails_WhenUserExists() {
        String username = "testUser";

        UserEntity user = new UserEntity();
        user.setUsername(username);
        user.setPassword("encodedPass");
        user.setActive(true);
        user.setRoles(List.of(new com.example.recetario_app.domain.entities.auth.RoleEntity(1L, "ROLE_USER","")));

        when(repository.findByUsername(username)).thenReturn(Optional.of(user));

        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        assertNotNull(userDetails);
        assertEquals(username, userDetails.getUsername());
        assertEquals(user.getPassword(), userDetails.getPassword());
        assertTrue(userDetails.isEnabled());
        assertEquals(1, userDetails.getAuthorities().size());
        assertTrue(userDetails.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_USER")));

        verify(repository).findByUsername(username);
    }

    @Test
    void loadUserByUsername_ShouldThrowUsernameNotFoundException_WhenUserDoesNotExist() {
        String username = "nonExisting";

        when(repository.findByUsername(username)).thenReturn(Optional.empty());

        UsernameNotFoundException exception = assertThrows(
                UsernameNotFoundException.class,
                () -> userDetailsService.loadUserByUsername(username)
        );

        assertEquals("No se encuentra usuario: " + username, exception.getMessage());

        verify(repository).findByUsername(username);
    }

    @Test
    void loadUserByUsername_ShouldReturnUserWithEmptyAuthorities_WhenUserHasNoRoles() {
        String username = "testUser";

        UserEntity user = new UserEntity();
        user.setUsername(username);
        user.setPassword("encodedPass");
        user.setActive(true);
        user.setRoles(List.of()); // sin roles

        when(repository.findByUsername(username)).thenReturn(Optional.of(user));

        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        assertNotNull(userDetails);
        assertEquals(0, userDetails.getAuthorities().size());

        verify(repository).findByUsername(username);
    }
}
