package com.example.recetario_app.infrastructure.dtos.ingredient;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IngredientRequest {
    @NotEmpty
    @NotNull
    @Size(min = 2,max = 50)
    private String name;
}
