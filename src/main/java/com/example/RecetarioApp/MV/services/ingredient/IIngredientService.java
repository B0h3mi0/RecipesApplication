package com.example.RecetarioApp.MV.services.ingredient;

import com.example.RecetarioApp.MV.infrastructure.dtos.ingredient.IngredientRequest;
import com.example.RecetarioApp.MV.infrastructure.dtos.ingredient.IngredientResponse;
import com.example.RecetarioApp.MV.services.GenericCrud;

public interface IIngredientService extends GenericCrud<IngredientResponse,IngredientRequest,Integer> {
    IngredientResponse findById(Integer id);
}
