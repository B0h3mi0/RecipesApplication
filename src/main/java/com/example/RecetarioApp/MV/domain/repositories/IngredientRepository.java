package com.example.RecetarioApp.MV.domain.repositories;

import com.example.RecetarioApp.MV.domain.entities.IngredientEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface IngredientRepository extends CrudRepository<IngredientEntity,Integer> {
    Optional<IngredientEntity> findByName(String name);
}
