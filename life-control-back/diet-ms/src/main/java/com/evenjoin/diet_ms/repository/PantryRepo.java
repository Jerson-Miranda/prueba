package com.evenjoin.diet_ms.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.evenjoin.diet_ms.entity.Pantry;

import feign.Param;

public interface PantryRepo extends JpaRepository<Pantry, Long> {

	// Get stock and expiration date by ingredient
	@Query("SELECT p.stock, p.expirationDate " +
			"FROM Pantry p " +
			"JOIN p.ingredient i " +
			"WHERE i.barcode = :barcode")
	public List<Object> getStockEDByIngredient(@Param("barcode") String barcode);

}
