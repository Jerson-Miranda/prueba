package com.evenjoin.diet_ms.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.evenjoin.diet_ms.entity.VariantIngredient;
import feign.Param;

public interface VariantIngredientRepo extends JpaRepository<VariantIngredient, Long> {

	//Get varient ingredientes by minimum stock
	@Query(
			"SELECT vi " +
			"FROM VariantIngredient vi " +
			"JOIN vi.ingredient i " +
			"JOIN Pantry p ON p.ingredient.idIngredient = i.idIngredient " +
			"WHERE p.stock <= :stock"
	)
	public List<VariantIngredient> getIngredientsByMinStock(@Param("stock") Integer stock);
}
