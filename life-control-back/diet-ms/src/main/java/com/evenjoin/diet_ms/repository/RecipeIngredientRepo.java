package com.evenjoin.diet_ms.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.evenjoin.diet_ms.entity.RecipeIngredient;
import com.evenjoin.diet_ms.entity.embedded.RecipeIngredientId;

public interface RecipeIngredientRepo extends JpaRepository<RecipeIngredient, RecipeIngredientId> {

}
