package com.example.RecetarioApp.infrastructure.dtos.recipe;

import com.example.RecetarioApp.domain.entities.recipe.RecipeEntity;
import com.example.RecetarioApp.infrastructure.dtos.detail_recipe.DetailRecipeResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class RecipeResponse {
    private Long id;
    private String name;
    private String preparationTime;
    private String description;
    private List<DetailRecipeResponse> details;
    private String difficulty;
    private String country;
    private String type;
    private String instruction;


    public static RecipeResponse fromEntity(RecipeEntity entity){
        return RecipeResponse.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .preparationTime(entity.getPreparationTime())
                .instruction(entity.getInstruction())
                .difficulty(entity.getDifficulty())
                .country(entity.getCountry())
                .type(entity.getType())
                .details(entity.getDetails()
                        .stream()
                        .map(DetailRecipeResponse::fromEntity)
                        .toList())
                .build();
    }
}
