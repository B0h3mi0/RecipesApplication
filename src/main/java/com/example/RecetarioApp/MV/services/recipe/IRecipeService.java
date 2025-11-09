package com.example.RecetarioApp.MV.services.recipe;

import com.example.RecetarioApp.MV.infrastructure.dtos.recipe.RecipeRequest;
import com.example.RecetarioApp.MV.infrastructure.dtos.recipe.RecipeResponse;
import com.example.RecetarioApp.MV.services.GenericCrud;

public interface IRecipeService extends GenericCrud<RecipeResponse, RecipeRequest,Long> {
}
