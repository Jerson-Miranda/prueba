package com.evenjoin.diet_ms.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.evenjoin.diet_ms.entity.Ingredient;

import feign.Param;

public interface IngredientRepo extends JpaRepository<Ingredient, Long> {

	//Get ingredients by category
	@Query(
			"SELECT i " +
			"FROM Ingredient i " +
			"JOIN i.subcategory sc " +
			"JOIN sc.category c " +
			"WHERE c.idCategory = :idCategory"
	)
	public List<Ingredient> getIngredientsByCategory(@Param("idCategory") Long idCategory);
	
	//Get ingredients by subcategory
	@Query(
			"SELECT i " +
			"FROM Ingredient i " +
			"JOIN i.subcategory sc " +
			"WHERE sc.idSubcategory = :idSubcategory"
	)
	public List<Ingredient> getIngredientsBySubcategory(@Param("idSubcategory") Long idSubcategory);
	
	//Get ingredient with maximum nutrient
	@Query(
			"SELECT i " +
			"FROM Ingredient i " +
			"JOIN i.nutritionalFact nf " +
			"ORDER BY CASE " +
				"WHEN :nutrient = 'kcal' THEN nf.kcal " +
			    "WHEN :nutrient = 'protein' THEN nf.protein " +
			    "WHEN :nutrient = 'carbohydrate' THEN nf.carbohydrate " +
			    "WHEN :nutrient = 'sugar' THEN nf.sugar " +
			    "WHEN :nutrient = 'addedSugar' THEN nf.addedSugar " +
			    "WHEN :nutrient = 'fat' THEN nf.fat " +
			    "WHEN :nutrient = 'saturatedFat' THEN nf.saturatedFat " +
			    "WHEN :nutrient = 'trans' THEN nf.trans " +
			    "WHEN :nutrient = 'fiber' THEN nf.fiber " +
			    "WHEN :nutrient = 'sodium' THEN nf.sodium " +
			    "ELSE 0 END DESC NULLS LAST"
	)
	public Ingredient getIngredientWithMaxNutrient(@Param("nutrient") String nutrient);
	
	//Get ingredient with maximum nutrient
	@Query(
			"SELECT i " +
			"FROM Ingredient i " +
			"JOIN i.nutritionalFact nf " +
			"ORDER BY CASE " +
				"WHEN :nutrient = 'kcal' THEN nf.kcal " +
			    "WHEN :nutrient = 'protein' THEN nf.protein " +
			    "WHEN :nutrient = 'carbohydrate' THEN nf.carbohydrate " +
			    "WHEN :nutrient = 'sugar' THEN nf.sugar " +
			    "WHEN :nutrient = 'addedSugar' THEN nf.addedSugar " +
			    "WHEN :nutrient = 'fat' THEN nf.fat " +
			    "WHEN :nutrient = 'saturatedFat' THEN nf.saturatedFat " +
			    "WHEN :nutrient = 'trans' THEN nf.trans " +
			    "WHEN :nutrient = 'fiber' THEN nf.fiber " +
			    "WHEN :nutrient = 'sodium' THEN nf.sodium " +
			    "ELSE 0 END ASC NULLS LAST"
	)
	public Ingredient getIngredientWithMinNutrient(@Param("nutrient") String nutrient);
	
	//Get quantity to consume by ingredient
	@Query(
			"SELECT nf.portion " +
			"FROM Ingredient i " +
			"JOIN i.nutritionalFact nf " +
			"WHERE i.idIngredient = :idIngredient"
	)
	public BigDecimal getQuantityToConsume(@Param("idIngredient") Long idIngredient);
	
}
