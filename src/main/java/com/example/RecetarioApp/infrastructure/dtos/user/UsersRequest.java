package com.example.RecetarioApp.infrastructure.dtos.user;

import com.example.RecetarioApp.domain.entities.RoleEntity;
import com.example.RecetarioApp.infrastructure.dtos.detail_recipe.DetailRecipeRequest;
import com.example.RecetarioApp.infrastructure.dtos.role.RoleResponse;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.ArrayList;
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


