package com.example.RecetarioApp.domain.repositories;

import com.example.RecetarioApp.domain.entities.DetailRecipeEntity;
import org.springframework.data.repository.CrudRepository;

public interface DetailRecipeRepository extends CrudRepository<DetailRecipeEntity,Long> {
}
