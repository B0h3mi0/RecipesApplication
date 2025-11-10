package com.example.RecetarioApp.infrastructure.dtos.role;

import com.example.RecetarioApp.domain.entities.auth.RoleEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class RoleResponse {
    private Long id;
    private String name;
    private String description;

    public static RoleResponse fromEntity(RoleEntity entity) {
        return new RoleResponse(
                entity.getId(),
                entity.getName(),
                entity.getDescription()
        );
    }
}
