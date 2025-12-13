package com.example.recetario_app.infrastructure.controllers;

import com.example.recetario_app.infrastructure.dtos.user.UserResponse;
import com.example.recetario_app.infrastructure.dtos.user.UsersRequest;
import com.example.recetario_app.services.role.RoleService;
import com.example.recetario_app.services.user.UsersService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UsersService userService;

    @MockBean
    private RoleService roleService;

    @Test
    @WithMockUser
    void listUsers_ShouldReturnListView() throws Exception {
        when(userService.getAllUsers()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(view().name("users/list"))
                .andExpect(model().attributeExists("users"));
    }

    @Test
    @WithMockUser
    void createUser_ShouldReturnFormView() throws Exception {
        when(roleService.getAllRoles()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/users/create"))
                .andExpect(status().isOk())
                .andExpect(view().name("users/form"))
                .andExpect(model().attributeExists("user"))
                .andExpect(model().attributeExists("roles"));
    }

    @Test
    @WithMockUser
    void saveUser_ShouldRedirect_WhenDataIsValid() throws Exception {
        mockMvc.perform(post("/users/save")
                .with(csrf())
                .param("username", "newUser")
                .param("password", "password")
                .param("active", "true")
                .param("roleId", "1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/users"));

        verify(userService, times(1)).createUsers(any(UsersRequest.class));
    }

    @Test
    @WithMockUser
    void saveUser_ShouldReturnFormView_WhenDataIsInvalid() throws Exception {
        mockMvc.perform(post("/users/save")
                .with(csrf())
                .param("username", ""))
                .andExpect(status().isOk())
                .andExpect(view().name("users/form"))
                .andExpect(model().attributeExists("roles"));

        verify(userService, never()).createUsers(any(UsersRequest.class));
    }

    @Test
    @WithMockUser
    void editUser_ShouldReturnFormView_WhenIdExists() throws Exception {
        Long id = 1L;
        UserResponse response = UserResponse.builder()
                .username("user1")
                .build();
        when(userService.getUsersById(id)).thenReturn(Optional.of(response));
        when(roleService.getAllRoles()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/users/edit/{id}", id))
                .andExpect(status().isOk())
                .andExpect(view().name("users/form"))
                .andExpect(model().attributeExists("user"))
                .andExpect(model().attributeExists("roles"));
    }
    @Test
    @WithMockUser
    void editUser_ShouldReturnFormView_WhenIdNotExists() throws Exception {
        Long id = 1L;

        when(userService.getUsersById(id)).thenReturn(Optional.empty());

        mockMvc.perform(get("/users/edit/{id}", id))
                .andExpect(status().isInternalServerError());
    }

    @Test
    @WithMockUser
    void deleteUser_ShouldRedirect_WhenIdExists() throws Exception {
        Long id = 1L;

        mockMvc.perform(get("/users/delete/{id}", id))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/users"));

        verify(userService, times(1)).deleteUsersById(id);
    }
}
