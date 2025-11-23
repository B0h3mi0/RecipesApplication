package com.example.recetario_app.domain.repositories.recipe;

import com.example.recetario_app.domain.entities.recipe.DetailRecipeEntity;
import org.springframework.data.repository.CrudRepository;

public interface DetailRecipeRepository extends CrudRepository<DetailRecipeEntity,Long> {
}
