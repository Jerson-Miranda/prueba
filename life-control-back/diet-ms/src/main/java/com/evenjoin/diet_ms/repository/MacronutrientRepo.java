package com.evenjoin.diet_ms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.evenjoin.diet_ms.entity.Macronutrient;

import feign.Param;

public interface MacronutrientRepo extends JpaRepository<Macronutrient, Long> {

	@Query(
			"SELECT ma " +
			"FROM Ingredient i " +
			"JOIN i.macronutrient ma " +
			"WHERE i.idIngredient = :idIngredient"
	)
	public Macronutrient getMacronutrientsByIngredient(@Param("idIngredient") Long idIngredient);
	
}
