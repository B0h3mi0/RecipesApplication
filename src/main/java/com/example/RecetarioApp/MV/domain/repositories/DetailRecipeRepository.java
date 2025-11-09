package com.example.RecetarioApp.MV.domain.repositories;

import com.example.RecetarioApp.MV.domain.entities.DetailRecipeEntity;
import org.springframework.data.repository.CrudRepository;

public interface DetailRecipeRepository extends CrudRepository<DetailRecipeEntity,Long> {
}
