package com.example.recetario_app.infrastructure.dtos.detail_recipe;

import com.example.recetario_app.domain.entities.recipe.DetailRecipeEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class DetailRecipeResponse {
    private Integer ingredientId;
    private String ingredientName;
    private String amount;

    public static DetailRecipeResponse fromEntity(DetailRecipeEntity entity){
        return DetailRecipeResponse.builder()
                .ingredientId(entity.getIngredient().getId())
                .ingredientName(entity.getIngredient().getName())
                .amount(entity.getIngredientAmount())
                .build();
    }
}
