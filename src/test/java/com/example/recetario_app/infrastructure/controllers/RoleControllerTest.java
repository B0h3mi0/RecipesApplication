package com.example.recetario_app.infrastructure.controllers;

import com.example.recetario_app.domain.entities.auth.RoleEntity;
import com.example.recetario_app.services.role.RoleService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RoleController.class)
class RoleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RoleService roleService;

    @Test
    @WithMockUser
    void getAllRoles_ShouldReturnListOfRoles() throws Exception {
        RoleEntity role1 = new RoleEntity();
        role1.setId(1L);
        role1.setName("ADMIN");
        RoleEntity role2 = new RoleEntity();
        role2.setId(2L);
        role2.setName("USER");

        when(roleService.getAllRoles()).thenReturn(Arrays.asList(role1, role2));

        mockMvc.perform(get("/role/all")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].name").value("ADMIN"))
                .andExpect(jsonPath("$[1].name").value("USER"));

        verify(roleService, times(1)).getAllRoles();
    }

    @Test
    @WithMockUser
    void getRoleById_ShouldReturnRole_WhenIdExists() throws Exception {
        Long id = 1L;
        RoleEntity role = new RoleEntity();
        role.setId(id);
        role.setName("ADMIN");

        when(roleService.getRoleById(id)).thenReturn(Optional.of(role));

        mockMvc.perform(get("/role/{id}", id)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("ADMIN"));

        verify(roleService, times(1)).getRoleById(id);
    }

    @Test
    @WithMockUser
    void getRoleById_ShouldReturnNotFound_WhenIdDoesNotExist() throws Exception {
        Long id = 1L;
        when(roleService.getRoleById(id)).thenReturn(Optional.empty());

        mockMvc.perform(get("/role/{id}", id)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(roleService, times(1)).getRoleById(id);
    }
}
