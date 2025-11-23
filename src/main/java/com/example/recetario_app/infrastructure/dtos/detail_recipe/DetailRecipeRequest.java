package com.example.recetario_app.infrastructure.dtos.detail_recipe;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DetailRecipeRequest {
    @NotNull
    @Size(min = 1)
    private Integer ingredientId;
    @NotBlank
    @Size(min = 2)
    private String amount;
}
