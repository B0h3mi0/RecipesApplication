package com.example.RecetarioApp.infrastructure.dtos.user;


import com.example.RecetarioApp.domain.entities.RecipeEntity;
import com.example.RecetarioApp.domain.entities.UserEntity;
import com.example.RecetarioApp.infrastructure.dtos.detail_recipe.DetailRecipeResponse;
import com.example.RecetarioApp.infrastructure.dtos.recipe.RecipeResponse;
import com.example.RecetarioApp.infrastructure.dtos.role.RoleResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.management.relation.Role;
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
                .roles(entity.getRole()
                        .stream()
                        .map(RoleResponse::fromEntity)
                        .toList())
                .build();
    }
}
