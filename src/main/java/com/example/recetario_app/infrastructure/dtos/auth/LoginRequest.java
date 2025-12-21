package com.example.recetario_app.infrastructure.dtos.auth;


import jakarta.validation.constraints.NotBlank;

public record LoginRequest(
        @NotBlank String username,
        @NotBlank String password
) {}