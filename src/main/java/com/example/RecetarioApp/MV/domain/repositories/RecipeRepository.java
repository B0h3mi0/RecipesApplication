package com.example.RecetarioApp.MV.domain.repositories;

import com.example.RecetarioApp.MV.domain.entities.RecipeEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RecipeRepository extends CrudRepository<RecipeEntity,Long> {
    Optional<RecipeEntity> findByName (String name);
}
