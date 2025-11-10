package com.example.RecetarioApp.domain.repositories.recipe;

import com.example.RecetarioApp.domain.entities.recipe.RecipeEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RecipeRepository extends CrudRepository<RecipeEntity,Long> {
    Optional<RecipeEntity> findByName (String name);
}
