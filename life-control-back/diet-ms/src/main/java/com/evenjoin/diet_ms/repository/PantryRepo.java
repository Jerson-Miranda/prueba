package com.evenjoin.diet_ms.repository;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.evenjoin.diet_ms.entity.Pantry;

import feign.Param;

public interface PantryRepo extends JpaRepository<Pantry, Long> {

	//Get expiration date by ingredient
	@Query(
			"SELECT p.expirationDate " +
			"FROM Pantry p " +
			"JOIN Ingredient i ON i.idIngredient = p.ingredient.idIngredient " +
			"JOIN VariantIngredient vi ON vi.ingredient.idIngredient = i.idIngredient " +
			"WHERE vi.barcode = :barcode"
	)
	public Date getExpirationDateByIngredient(@Param("barcode") String barcode);
	
	//Get expiration date by ingredient
	@Query(
			"SELECT p.stock " +
			"FROM Pantry p " +
			"JOIN Ingredient i ON i.idIngredient = p.ingredient.idIngredient " +
			"JOIN VariantIngredient vi ON vi.ingredient.idIngredient = i.idIngredient " +
			"WHERE vi.barcode = :barcode"
	)
	public Integer getStockByIngredient(@Param("barcode") String barcode);
	
}
