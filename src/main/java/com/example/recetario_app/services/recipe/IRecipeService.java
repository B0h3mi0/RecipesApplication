package com.example.recetario_app.services.recipe;

import com.example.recetario_app.infrastructure.dtos.recipe.RecipeRequest;
import com.example.recetario_app.infrastructure.dtos.recipe.RecipeResponse;
import com.example.recetario_app.services.GenericCrud;

import java.util.List;

public interface IRecipeService extends GenericCrud<RecipeResponse, RecipeRequest,Long> {
    List<RecipeResponse> findPopulars();
    List<RecipeResponse> findRecent();
}
