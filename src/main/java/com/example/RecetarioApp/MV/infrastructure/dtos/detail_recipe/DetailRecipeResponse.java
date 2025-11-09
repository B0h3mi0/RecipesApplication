package com.example.RecetarioApp.MV.infrastructure.dtos.detail_recipe;

import com.example.RecetarioApp.MV.domain.entities.DetailRecipeEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class DetailRecipeResponse {
    private String ingredientName;
    private String ingredientAmount;

    public static DetailRecipeResponse fromEntity(DetailRecipeEntity entity){
        return DetailRecipeResponse.builder()
                .ingredientName(entity.getIngredient().getName())
                .ingredientAmount(entity.getIngredientAmount())
                .build();
    }
}
