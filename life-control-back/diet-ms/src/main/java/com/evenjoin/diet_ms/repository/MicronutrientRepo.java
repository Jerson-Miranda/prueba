package com.evenjoin.diet_ms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.evenjoin.diet_ms.entity.Micronutrient;

import feign.Param;

public interface MicronutrientRepo extends JpaRepository<Micronutrient, Long> {

	// Get micronutrients by ingredient
	@Query(
			"SELECT mi " +
			"FROM Micronutrient mi " +
			"JOIN CommonIngredient ci ON ci.micronutrient.idMicronutrient = mi.idMicronutrient " +
			"JOIN Ingredient i ON i.commonIngredient.idCommonIngredient = ci.idCommonIngredient " +
			"WHERE i.barcode = :barcode"
	)
	public Micronutrient getMicronutrientsByIngredient(@Param("barcode") String barcode);
		
}
