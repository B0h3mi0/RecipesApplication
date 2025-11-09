package com.example.RecetarioApp.services.recipe;

import com.example.RecetarioApp.infrastructure.dtos.recipe.RecipeRequest;
import com.example.RecetarioApp.infrastructure.dtos.recipe.RecipeResponse;
import com.example.RecetarioApp.services.GenericCrud;

public interface IRecipeService extends GenericCrud<RecipeResponse, RecipeRequest,Long> {
}
