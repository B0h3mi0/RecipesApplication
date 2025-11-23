package com.example.recetario_app.services.ingredient;

import com.example.recetario_app.infrastructure.dtos.ingredient.IngredientRequest;
import com.example.recetario_app.infrastructure.dtos.ingredient.IngredientResponse;
import com.example.recetario_app.services.GenericCrud;

public interface IIngredientService extends GenericCrud<IngredientResponse,IngredientRequest,Integer> {
}
