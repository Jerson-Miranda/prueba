package com.evenjoin.diet_ms.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.evenjoin.diet_ms.entity.Ingredient;

import feign.Param;

public interface IngredientRepo extends JpaRepository<Ingredient, Long> {

	//Count ingredients
	@Query(
			"SELECT COUNT(i) " +
			"FROM Ingredient i"
	)
	public Integer countIngredients();
	
	//Get ingredients by subcategory
	@Query(
			"SELECT i " +
			"FROM Ingredient i " +
			"JOIN i.subcategory sc " +
			"WHERE sc.idSubcategory = :idSubcategory"
	)
	public List<Ingredient> getIngredientsBySubcategory(@Param("idSubcategory") Long idSubcategory);
	
	//Get ingredients by category
	@Query(
			"SELECT i " +
			"FROM Ingredient i " +
			"JOIN i.subcategory sc " +
			"JOIN sc.category c " +
			"WHERE c.idCategory = :idCategory"
	)
	public List<Ingredient> getIngredientsByCategory(@Param("idCategory") Long idCategory);
	
	//Get ingredient with maximum nutrient
	@Query(
			"SELECT i " +
			"FROM Ingredient i " +
			"JOIN i.macronutrient ma " +
			"ORDER BY CASE " +
				"WHEN :nutrient = 'kcal' THEN ma.kcal " +
			    "WHEN :nutrient = 'protein' THEN ma.protein " +
			    "WHEN :nutrient = 'carbohydrate' THEN ma.carbohydrate " +
			    "WHEN :nutrient = 'sugar' THEN ma.sugar " +
			    "WHEN :nutrient = 'addedSugar' THEN ma.addedSugar " +
			    "WHEN :nutrient = 'fat' THEN ma.fat " +
			    "WHEN :nutrient = 'saturatedFat' THEN ma.saturatedFat " +
			    "WHEN :nutrient = 'trans' THEN ma.trans " +
			    "WHEN :nutrient = 'fiber' THEN ma.fiber " +
			    "WHEN :nutrient = 'sodium' THEN ma.sodium " +
			    "ELSE 0 END DESC " +
			    "LIMIT 1"
	)
	public Ingredient getIngredientWithMaxNutrient(@Param("nutrient") String nutrient);
	
	//Get ingredient with minimum nutrient
	@Query(
			"SELECT i " +
			"FROM Ingredient i " +
			"JOIN i.macronutrient ma " +
			"ORDER BY CASE " +
				"WHEN :nutrient = 'kcal' THEN ma.kcal " +
			    "WHEN :nutrient = 'protein' THEN ma.protein " +
			    "WHEN :nutrient = 'carbohydrate' THEN ma.carbohydrate " +
			    "WHEN :nutrient = 'sugar' THEN ma.sugar " +
			    "WHEN :nutrient = 'addedSugar' THEN ma.addedSugar " +
			    "WHEN :nutrient = 'fat' THEN ma.fat " +
			    "WHEN :nutrient = 'saturatedFat' THEN ma.saturatedFat " +
			    "WHEN :nutrient = 'trans' THEN ma.trans " +
			    "WHEN :nutrient = 'fiber' THEN ma.fiber " +
			    "WHEN :nutrient = 'sodium' THEN ma.sodium " +
			    "ELSE 0 END ASC " +
			    "LIMIT 1"
	)
	public Ingredient getIngredientWithMinNutrient(@Param("nutrient") String nutrient);
	
	//Get quantity to consume by ingredient
	@Query(
			"SELECT ma.portion " +
			"FROM Ingredient i " +
			"JOIN i.macronutrient ma " +
			"WHERE i.idIngredient = :idIngredient"
	)
	public BigDecimal getQuantityToConsume(@Param("idIngredient") Long idIngredient);
	
	//Get ingredients by recipe
	@Query(
			"SELECT i " +
			"FROM Ingredient i " +
			"JOIN RecipeIngredient ri ON i.idIngredient = ri.ingredient.idIngredient " +
			"JOIN Recipe r ON ri.recipe.idRecipe = r.idRecipe " +
			"WHERE r.idRecipe = :idRecipe"
	)
	public List<Ingredient> getIngredientsByRecipe(@Param("idRecipe") Long idRecipe);
	
}
