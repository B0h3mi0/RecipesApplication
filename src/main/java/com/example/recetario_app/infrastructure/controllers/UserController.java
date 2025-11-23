package com.example.recetario_app.infrastructure.controllers;

import com.example.recetario_app.infrastructure.dtos.user.UsersRequest;
import com.example.recetario_app.services.role.RoleService;
import com.example.recetario_app.services.user.UsersService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UsersService userService;
    private final RoleService roleService;


    @GetMapping
    public String listUsers(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "users/list";
    }


    @GetMapping("/create")
    public String createUser(Model model) {
        model.addAttribute("user", new UsersRequest());
        model.addAttribute("roles", roleService.getAllRoles());
        return "users/form";
    }


    @PostMapping("/save")
    public String saveUser(
            @Valid @ModelAttribute("user") UsersRequest userRequest,
            BindingResult result,
            Model model) {

        if (result.hasErrors()) {
            log.warn("Validation errors: {}", result.getAllErrors());
            model.addAttribute("roles", roleService.getAllRoles());
            return "users/form";
        }

        log.info("Saving user with username: {}", userRequest.getUsername());
        userService.createUsers(userRequest);
        log.info("User saved successfully. Redirecting to /users");

        return "redirect:/users";
    }


    @GetMapping("/edit/{id}")
    public String editUser(@PathVariable Long id, Model model) {
        log.info("Editing user with ID: {}", id);
        var user = userService.getUsersById(id)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + id));
        model.addAttribute("user", user);
        model.addAttribute("roles", roleService.getAllRoles());
        return "users/form";
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        log.info("Deleting user with ID: {}", id);
        userService.deleteUsersById(id);
        log.info("User deleted successfully.");
        return "redirect:/users";
    }
}

