package com.evenjoin.diet_ms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.evenjoin.diet_ms.entity.TypeIngredient;

public interface TypeIngredientRepo extends JpaRepository<TypeIngredient, Long> {

	//Count type ingredients
	@Query(
			"SELECT COUNT(ti) " +
			"FROM TypeIngredient ti"
	)
	public Integer countTypeIngredients();
	
}
