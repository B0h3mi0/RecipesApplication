package com.example.RecetarioApp.services.ingredient;

import com.example.RecetarioApp.infrastructure.dtos.ingredient.IngredientRequest;
import com.example.RecetarioApp.infrastructure.dtos.ingredient.IngredientResponse;
import com.example.RecetarioApp.services.GenericCrud;

public interface IIngredientService extends GenericCrud<IngredientResponse,IngredientRequest,Integer> {
}
