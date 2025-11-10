package com.example.RecetarioApp.domain.repositories.recipe;

import com.example.RecetarioApp.domain.entities.recipe.DetailRecipeEntity;
import org.springframework.data.repository.CrudRepository;

public interface DetailRecipeRepository extends CrudRepository<DetailRecipeEntity,Long> {
}
