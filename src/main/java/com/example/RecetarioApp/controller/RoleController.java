package com.example.RecetarioApp.controller;

import com.example.RecetarioApp.domain.RoleEntity;
import com.example.RecetarioApp.service.RoleService;
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
@RequestMapping("/role")
public class RoleController {

    private static final Logger logger = LoggerFactory.getLogger(RoleController.class);

    private final RoleService roleService;

    @GetMapping("/all")
    public ResponseEntity<List<RoleEntity>> getAllRoles() {
        logger.info("Fetching all roles...");
        List<RoleEntity> roles = roleService.getAllRoles();
        logger.info("Successfully retrieved {} roles.", roles.size());
        return new ResponseEntity<>(roles, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoleEntity> getRoleById(@PathVariable Long id) {
        logger.info("Fetching role by ID: {}", id);
        Optional<RoleEntity> role = roleService.getRoleById(id);
        return role.map(value -> {
                    logger.info("Role found with ID: {}", id);
                    return new ResponseEntity<>(value, HttpStatus.OK);
                })
                .orElseGet(() -> {
                    logger.warn("Role not found with ID: {}", id);
                    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                });
    }

    @PostMapping
    public ResponseEntity<RoleEntity> createRole(@RequestBody RoleEntity role) {
        logger.info("Creating a new role with request: {}", role);
        RoleEntity savedRole = roleService.createRole(role);
        logger.info("Role successfully created. Role ID: {}", savedRole.getId());
        return new ResponseEntity<>(savedRole, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRole(@PathVariable Long id) {
        logger.info("Deleting role with ID: {}", id);
        roleService.deleteRoleById(id);
        logger.info("Role successfully deleted. Role ID: {}", id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
