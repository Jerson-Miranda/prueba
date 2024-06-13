package com.evenjoin.diet_ms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.evenjoin.diet_ms.entity.CommonIngredient;


public interface CommonIngredientRepo extends JpaRepository<CommonIngredient, Long> {

	
}
