package com.example.RecetarioApp.domain.repositories.recipe;

import com.example.RecetarioApp.domain.entities.recipe.IngredientEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface IngredientRepository extends CrudRepository<IngredientEntity,Integer> {
    Optional<IngredientEntity> findByName(String name);
}
