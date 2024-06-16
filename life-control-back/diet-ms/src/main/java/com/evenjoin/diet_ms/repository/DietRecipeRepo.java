package com.evenjoin.diet_ms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.evenjoin.diet_ms.entity.DietRecipe;
import com.evenjoin.diet_ms.entity.embedded.DietRecipeId;

public interface DietRecipeRepo extends JpaRepository<DietRecipe, DietRecipeId> {

}
