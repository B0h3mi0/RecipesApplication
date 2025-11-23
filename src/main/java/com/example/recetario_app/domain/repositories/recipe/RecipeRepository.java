package com.example.recetario_app.domain.repositories.recipe;

import com.example.recetario_app.domain.entities.recipe.RecipeEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RecipeRepository extends CrudRepository<RecipeEntity,Long> {
    Optional<RecipeEntity> findByName (String name);
}
