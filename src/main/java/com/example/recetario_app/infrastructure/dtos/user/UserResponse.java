package com.example.recetario_app.infrastructure.dtos.user;


import com.example.recetario_app.domain.entities.auth.UserEntity;
import com.example.recetario_app.infrastructure.dtos.role.RoleResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class UserResponse {

    private Long id;
    private String username;
    private boolean active;
    private List<RoleResponse> roles;

    public static UserResponse fromEntity(UserEntity entity){
        return UserResponse.builder()
                .id(entity.getId())
                .username(entity.getUsername())
                .active(entity.isActive())
                .roles(entity.getRoles()
                        .stream()
                        .map(RoleResponse::fromEntity)
                        .toList())
                .build();
    }
}
