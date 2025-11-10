package com.example.RecetarioApp.services.recipe;

import com.example.RecetarioApp.infrastructure.dtos.recipe.RecipeRequest;
import com.example.RecetarioApp.infrastructure.dtos.recipe.RecipeResponse;
import com.example.RecetarioApp.services.GenericCrud;

import java.util.List;

public interface IRecipeService extends GenericCrud<RecipeResponse, RecipeRequest,Long> {
    List<RecipeResponse> findPopulars();
    List<RecipeResponse> findRecent();
}
