package com.evenjoin.diet_ms.repository;

import java.util.Date;
import java.util.List;

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

	// Get vitamins by diet
	@Query("SELECT " +
			"SUM((v.vitaminA * ri.amount / 100) * dr.portion) AS total_a, " +
			"SUM((v.vitaminB * ri.amount / 100) * dr.portion) AS total_b, " +
			"SUM((v.vitaminC * ri.amount / 100) * dr.portion) AS total_c, " +
			"SUM((v.vitaminD * ri.amount / 100) * dr.portion) AS total_d, " +
			"SUM((v.vitaminE * ri.amount / 100) * dr.portion) AS total_e, " +
			"SUM((v.vitaminK * ri.amount / 100) * dr.portion) AS total_k " +
			"FROM Vitamin v " +
			"JOIN Micronutrient mi ON mi.vitamin.idVitamin = v.idVitamin " +
			"JOIN CommonIngredient ci ON ci.micronutrient.idMicronutrient = mi.idMicronutrient " +
			"JOIN Ingredient i ON i.commonIngredient.idCommonIngredient = ci.idCommonIngredient " +
			"JOIN RecipeIngredient ri ON ri.ingredient.idIngredient = i.idIngredient " +
			"JOIN Recipe r ON r.idRecipe = ri.recipe.idRecipe " +
			"JOIN DietRecipe dr ON dr.recipe.idRecipe = r.idRecipe " +
			"JOIN Diet d ON d.idDiet = dr.diet.idDiet " +
			"WHERE d.idDiet = :idDiet")
	public Object getVitaminsByDiet(@Param("idDiet") Long idDiet);

	// Get vitamins by diet between dates
	@Query("SELECT d.idDiet, d.date, " +
			"SUM((v.vitaminA * ri.amount / 100) * dr.portion) AS total_a, " +
			"SUM((v.vitaminB * ri.amount / 100) * dr.portion) AS total_b, " +
			"SUM((v.vitaminC * ri.amount / 100) * dr.portion) AS total_c, " +
			"SUM((v.vitaminD * ri.amount / 100) * dr.portion) AS total_d, " +
			"SUM((v.vitaminE * ri.amount / 100) * dr.portion) AS total_e, " +
			"SUM((v.vitaminK * ri.amount / 100) * dr.portion) AS total_k " +
			"FROM Vitamin v " +
			"JOIN Micronutrient mi ON mi.vitamin.idVitamin = v.idVitamin " +
			"JOIN CommonIngredient ci ON ci.micronutrient.idMicronutrient = mi.idMicronutrient " +
			"JOIN Ingredient i ON i.commonIngredient.idCommonIngredient = ci.idCommonIngredient " +
			"JOIN RecipeIngredient ri ON ri.ingredient.idIngredient = i.idIngredient " +
			"JOIN Recipe r ON r.idRecipe = ri.recipe.idRecipe " +
			"JOIN DietRecipe dr ON dr.recipe.idRecipe = r.idRecipe " +
			"JOIN Diet d ON d.idDiet = dr.diet.idDiet " +
			"WHERE d.date BETWEEN :startDate AND :endDate " +
			"GROUP BY d.idDiet")
	public List<Object> getVitaminsByDietRange(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

}
