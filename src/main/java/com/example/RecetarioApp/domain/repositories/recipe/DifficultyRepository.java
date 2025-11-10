package com.example.RecetarioApp.domain.repositories.recipe;

import com.example.RecetarioApp.domain.entities.recipe.DifficultyEntity;
import org.springframework.data.repository.CrudRepository;

public interface DifficultyRepository extends CrudRepository<DifficultyEntity,Integer> {
}
