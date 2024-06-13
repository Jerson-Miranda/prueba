package com.evenjoin.diet_ms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.evenjoin.diet_ms.entity.Vitamin;

import feign.Param;

public interface VitaminRepo extends JpaRepository<Vitamin, Long> {

	// Get vitamins by ingredient
	@Query("SELECT v " +
			"FROM Vitamin v " +
			"JOIN Micronutrient mi ON mi.vitamin.idVitamin = v.idVitamin " +
			"JOIN CommonIngredient ci ON ci.micronutrient.idMicronutrient = mi.idMicronutrient " +
			"JOIN Ingredient i ON i.commonIngredient.idCommonIngredient = ci.idCommonIngredient " +
			"WHERE i.barcode = :barcode")
	public Vitamin getVitaminsByIngredient(@Param("barcode") String barcode);

}
