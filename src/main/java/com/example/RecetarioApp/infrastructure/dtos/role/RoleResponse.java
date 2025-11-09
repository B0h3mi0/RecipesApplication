package com.example.RecetarioApp.infrastructure.dtos.role;

import com.example.RecetarioApp.domain.entities.IngredientEntity;
import com.example.RecetarioApp.domain.entities.RoleEntity;
import com.example.RecetarioApp.infrastructure.dtos.ingredient.IngredientResponse;
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
    private String roleName;
    private String description;

    public static RoleResponse fromEntity(RoleEntity entity) {
        return new RoleResponse(
                entity.getId(),
                entity.getRoleName(),
                entity.getDescription()
        );
    }
}
