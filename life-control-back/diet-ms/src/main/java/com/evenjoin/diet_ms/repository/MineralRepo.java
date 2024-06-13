package com.evenjoin.diet_ms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.evenjoin.diet_ms.entity.Mineral;

import feign.Param;

public interface MineralRepo extends JpaRepository<Mineral, Long> {

	// Get vitamins by ingredient
	@Query("SELECT m " +
			"FROM Mineral m " +
			"JOIN Micronutrient mi ON mi.mineral.idMineral = m.idMineral " +
			"JOIN CommonIngredient ci ON ci.micronutrient.idMicronutrient = mi.idMicronutrient " +
			"JOIN Ingredient i ON i.commonIngredient.idCommonIngredient = ci.idCommonIngredient " +
			"WHERE i.barcode = :barcode")
	public Mineral getMineralsByIngredient(@Param("barcode") String barcode);

}
