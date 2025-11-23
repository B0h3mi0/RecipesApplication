package com.example.recetario_app.infrastructure.dtos.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;


@Getter
@Setter
public class UsersRequest {


    @NotBlank(message = "Username is required")
    @Size(max = 150, message = "Username cannot exceed 150 characters")
    private String username;

    @NotBlank(message = "Password is required")
    @Size(max = 60, message = "Password cannot exceed 60 characters")
    private String password;

    private boolean active; // Expected values: "Y" or "N"

    @NotNull
    private List<Long> roleId;
}


