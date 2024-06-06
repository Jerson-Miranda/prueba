package com.evenjoin.diet_ms.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.evenjoin.diet_ms.entity.RecipeBookIngredient;
import com.evenjoin.diet_ms.entity.embedded.RecipeBookIngredientId;

public interface RecipeBookIngredientRepo extends JpaRepository<RecipeBookIngredient, RecipeBookIngredientId> {

}
