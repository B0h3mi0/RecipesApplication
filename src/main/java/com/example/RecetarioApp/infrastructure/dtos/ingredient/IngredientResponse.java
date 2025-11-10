package com.example.RecetarioApp.infrastructure.dtos.ingredient;

import com.example.RecetarioApp.domain.entities.recipe.IngredientEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class IngredientResponse {
    private Integer id;
    private String name;

    public static IngredientResponse fromEntity(IngredientEntity entity) {
        return new IngredientResponse(
                entity.getId(),
                entity.getName()
        );
    }
}
