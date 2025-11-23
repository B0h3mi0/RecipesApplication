package com.example.recetario_app.infrastructure.dtos.recipe;

import com.example.recetario_app.infrastructure.dtos.detail_recipe.DetailRecipeRequest;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class RecipeRequest {
    @NotEmpty
    @Size(min = 2,max = 60)
    private String name;

    @NotEmpty
    @Size(min = 5,max = 50)
    private String preparationTime;

    @NotEmpty
    @Size(min = 10, max = 200)
    private String description;

    @NotEmpty
    @Size(min = 5, max = 10)
    private String difficulty;

    @NotEmpty
    @Size(min = 2, max = 30)
    private String country;

    @NotEmpty
    @Size(min = 4, max = 30)
    private String type;

    @NotEmpty
    @Size(min = 4)
    private String instruction;
    
    @NotNull
    private List<DetailRecipeRequest> details = new ArrayList<>();

}
