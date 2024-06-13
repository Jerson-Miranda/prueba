package com.evenjoin.diet_ms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.evenjoin.diet_ms.entity.Macronutrient;

import feign.Param;

public interface MacronutrientRepo extends JpaRepository<Macronutrient, Long> {

	// Get macronutrients by ingredient
	@Query(
			"SELECT ma " +
			"FROM Macronutrient ma " +
			"JOIN CommonIngredient ci ON ci.macronutrient.idMacronutrient = ma.idMacronutrient " +
			"JOIN Ingredient i ON i.commonIngredient.idCommonIngredient = ci.idCommonIngredient " +
			"WHERE i.barcode = :barcode"
	)
	public Macronutrient getMacronutrientsByIngredient(@Param("barcode") String barcode);
	
}
