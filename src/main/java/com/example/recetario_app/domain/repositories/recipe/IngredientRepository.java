package com.example.recetario_app.domain.repositories.recipe;

import com.example.recetario_app.domain.entities.recipe.IngredientEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface IngredientRepository extends CrudRepository<IngredientEntity,Integer> {
    Optional<IngredientEntity> findByName(String name);
}
