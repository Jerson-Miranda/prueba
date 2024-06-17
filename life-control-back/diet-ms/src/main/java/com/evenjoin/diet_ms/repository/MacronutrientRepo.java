package com.evenjoin.diet_ms.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.evenjoin.diet_ms.entity.Macronutrient;

import feign.Param;

public interface MacronutrientRepo extends JpaRepository<Macronutrient, Long> {

	// Get macronutrients by ingredient
	@Query("SELECT ma " +
			"FROM Macronutrient ma " +
			"JOIN CommonIngredient ci ON ci.macronutrient.idMacronutrient = ma.idMacronutrient " +
			"JOIN Ingredient i ON i.commonIngredient.idCommonIngredient = ci.idCommonIngredient " +
			"WHERE i.idIngredient = :idIngredient")
	public Macronutrient getMacronutrientsByIngredient(@Param("idIngredient") Long idIngredient);

	// Get macronutrients by recipe
	@Query("SELECT " +
			"SUM((ma.kcal * ri.amount) / 100) AS total_kcal, " +
			"SUM((ma.protein * ri.amount) / 100) AS total_protein, " +
			"SUM((ma.carbohydrate * ri.amount) / 100) AS total_carbohydrate, " +
			"SUM((ma.sugar * ri.amount) / 100) AS total_sugar, " +
			"SUM((ma.addedSugar * ri.amount) / 100) AS total_added_sugar, " +
			"SUM((ma.fat * ri.amount) / 100) AS total_fat, " +
			"SUM((ma.saturatedFat * ri.amount) / 100) AS total_saturated_fat, " +
			"SUM((ma.trans * ri.amount) / 100) AS total_trans, " +
			"SUM((ma.fiber * ri.amount) / 100) AS total_fiber, " +
			"SUM((ma.sodium * ri.amount) / 100) AS total_sodium " +
			"FROM Macronutrient ma " +
			"JOIN CommonIngredient ci ON ci.macronutrient.idMacronutrient = ma.idMacronutrient " +
			"JOIN Ingredient i ON i.commonIngredient.idCommonIngredient = ci.idCommonIngredient " +
			"JOIN RecipeIngredient ri ON ri.ingredient.idIngredient = i.idIngredient " +
			"JOIN Recipe r ON r.idRecipe = ri.recipe.idRecipe " +
			"WHERE r.idRecipe = :idRecipe")
	public Object getMacronutrientsByRecipe(@Param("idRecipe") Long idRecipe);

	// Get macronutrients by diet
	@Query("SELECT " +
			"SUM((ma.kcal * ri.amount / 100) * dr.portion) AS total_kcal, " +
			"SUM((ma.protein * ri.amount / 100) * dr.portion) AS total_protein, " +
			"SUM((ma.carbohydrate * ri.amount / 100) * dr.portion) AS total_carbohydrate, " +
			"SUM((ma.sugar * ri.amount / 100) * dr.portion) AS total_sugar, " +
			"SUM((ma.addedSugar * ri.amount / 100) * dr.portion) AS total_added_sugar, " +
			"SUM((ma.fat * ri.amount / 100) * dr.portion) AS total_fat, " +
			"SUM((ma.saturatedFat * ri.amount / 100) * dr.portion) AS total_saturated_fat, " +
			"SUM((ma.trans * ri.amount / 100) * dr.portion) AS total_trans, " +
			"SUM((ma.fiber * ri.amount / 100) * dr.portion) AS total_fiber, " +
			"SUM((ma.sodium * ri.amount / 100) * dr.portion) AS total_sodium " +
			"FROM Macronutrient ma " +
			"JOIN CommonIngredient ci ON ci.macronutrient.idMacronutrient = ma.idMacronutrient " +
			"JOIN Ingredient i ON i.commonIngredient.idCommonIngredient = ci.idCommonIngredient " +
			"JOIN RecipeIngredient ri ON ri.ingredient.idIngredient = i.idIngredient " +
			"JOIN Recipe r ON r.idRecipe = ri.recipe.idRecipe " +
			"JOIN DietRecipe dr ON dr.recipe.idRecipe = r.idRecipe " +
			"JOIN Diet d ON d.idDiet = dr.diet.idDiet " +
			"WHERE d.idDiet = :idDiet")
	public Object getMacronutrientsByDiet(@Param("idDiet") Long idDiet);

	// Get macronutrients by diet between dates
	@Query("SELECT d.idDiet, d.date, " +
			"SUM((ma.kcal * ri.amount / 100) * dr.portion) AS total_kcal, " +
			"SUM((ma.protein * ri.amount / 100) * dr.portion) AS total_protein, " +
			"SUM((ma.carbohydrate * ri.amount / 100) * dr.portion) AS total_carbohydrate, " +
			"SUM((ma.sugar * ri.amount / 100) * dr.portion) AS total_sugar, " +
			"SUM((ma.addedSugar * ri.amount / 100) * dr.portion) AS total_added_sugar, " +
			"SUM((ma.fat * ri.amount / 100) * dr.portion) AS total_fat, " +
			"SUM((ma.saturatedFat * ri.amount / 100) * dr.portion) AS total_saturated_fat, " +
			"SUM((ma.trans * ri.amount / 100) * dr.portion) AS total_trans, " +
			"SUM((ma.fiber * ri.amount / 100) * dr.portion) AS total_fiber, " +
			"SUM((ma.sodium * ri.amount / 100) * dr.portion) AS total_sodium " +
			"FROM Macronutrient ma " +
			"JOIN CommonIngredient ci ON ci.macronutrient.idMacronutrient = ma.idMacronutrient " +
			"JOIN Ingredient i ON i.commonIngredient.idCommonIngredient = ci.idCommonIngredient " +
			"JOIN RecipeIngredient ri ON ri.ingredient.idIngredient = i.idIngredient " +
			"JOIN Recipe r ON r.idRecipe = ri.recipe.idRecipe " +
			"JOIN DietRecipe dr ON dr.recipe.idRecipe = r.idRecipe " +
			"JOIN Diet d ON d.idDiet = dr.diet.idDiet " +
			"WHERE d.date BETWEEN :startDate AND :endDate " +
			"GROUP BY d.idDiet")
	public List<Object> getMacronutrientsByDietRange(@Param("startDate") Date startDate, @Param("endDate") Date endDate);
}
