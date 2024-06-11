package com.evenjoin.diet_ms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.evenjoin.diet_ms.entity.Recipe;


public interface RecipeRepo extends JpaRepository<Recipe, Long> {

	
}
