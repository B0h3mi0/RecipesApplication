package com.example.recetario_app.services.role;

import com.example.recetario_app.domain.entities.auth.RoleEntity;
import com.example.recetario_app.domain.repositories.auth.RoleRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RoleServiceImplTest {

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private RoleServiceImpl roleService;

    @Test
    void getAllRoles_ShouldReturnListOfRoles() {
        RoleEntity role1 = new RoleEntity();
        role1.setId(1L);
        role1.setName("ADMIN");

        RoleEntity role2 = new RoleEntity();
        role2.setId(2L);
        role2.setName("USER");

        when(roleRepository.findAll()).thenReturn(Arrays.asList(role1, role2));

        List<RoleEntity> result = roleService.getAllRoles();

        assertEquals(2, result.size());
        assertEquals("ADMIN", result.get(0).getName());
        assertEquals("USER", result.get(1).getName());
        verify(roleRepository, times(1)).findAll();
    }

    @Test
    void getRoleById_ShouldReturnRole_WhenIdExists() {
        Long id = 1L;
        RoleEntity role = new RoleEntity();
        role.setId(id);
        role.setName("ADMIN");

        when(roleRepository.findById(id)).thenReturn(Optional.of(role));

        Optional<RoleEntity> result = roleService.getRoleById(id);

        assertTrue(result.isPresent());
        assertEquals("ADMIN", result.get().getName());
        verify(roleRepository, times(1)).findById(id);
    }

    @Test
    void getRoleById_ShouldReturnEmpty_WhenIdDoesNotExist() {
        Long id = 1L;
        when(roleRepository.findById(id)).thenReturn(Optional.empty());

        Optional<RoleEntity> result = roleService.getRoleById(id);

        assertFalse(result.isPresent());
        verify(roleRepository, times(1)).findById(id);
    }
}
