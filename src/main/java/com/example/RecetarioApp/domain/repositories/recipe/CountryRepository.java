package com.example.RecetarioApp.domain.repositories.recipe;

import com.example.RecetarioApp.domain.entities.recipe.CountryEntity;
import org.springframework.data.repository.CrudRepository;

public interface CountryRepository extends CrudRepository<CountryEntity,Integer> {
}
