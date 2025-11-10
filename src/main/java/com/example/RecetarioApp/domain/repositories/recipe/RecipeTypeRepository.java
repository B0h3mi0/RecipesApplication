package com.example.RecetarioApp.domain.repositories.recipe;

import com.example.RecetarioApp.domain.entities.recipe.RecipeTypeEntity;
import org.springframework.data.repository.CrudRepository;

public interface RecipeTypeRepository extends CrudRepository<RecipeTypeEntity,Integer> {
}
