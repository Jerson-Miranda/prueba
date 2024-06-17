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
			"WHERE i.idIngredient = :idIngredient")
	public Vitamin getVitaminsByIngredient(@Param("idIngredient") Long idIngredient);

	// Get vitamins by recipe
	@Query("SELECT " +
			"SUM((v.vitaminA * ri.amount) / 100) AS total_a, " +
			"SUM((vb.vitaminB1 * ri.amount) / 100) AS total_b1, " +
			"SUM((vb.vitaminB2 * ri.amount) / 100) AS total_b2, " +
			"SUM((vb.vitaminB3 * ri.amount) / 100) AS total_b3, " +
			"SUM((vb.vitaminB4 * ri.amount) / 100) AS total_b4, " +
			"SUM((vb.vitaminB5 * ri.amount) / 100) AS total_b5, " +
			"SUM((vb.vitaminB6 * ri.amount) / 100) AS total_b6, " +
			"SUM((vb.vitaminB7 * ri.amount) / 100) AS total_b7, " +
			"SUM((vb.vitaminB8 * ri.amount) / 100) AS total_b8, " +
			"SUM((vb.vitaminB9 * ri.amount) / 100) AS total_b9, " +
			"SUM((vb.vitaminB12 * ri.amount) / 100) AS total_b12, " +
			"SUM((v.vitaminC * ri.amount) / 100) AS total_c, " +
			"SUM((vd.vitaminD2 * ri.amount) / 100) AS total_d2, " +
			"SUM((vd.vitaminD3 * ri.amount) / 100) AS total_d3, " +
			"SUM((v.vitaminE * ri.amount) / 100) AS total_e, " +
			"SUM((vk.vitaminK1 * ri.amount) / 100) AS total_k1, " +
			"SUM((vk.vitaminK2 * ri.amount) / 100) AS total_k2 " +
			"FROM Vitamin v " +
			"JOIN v.vitaminB vb " +
			"JOIN v.vitaminD vd " +
			"JOIN v.vitaminK vk " +
			"JOIN Micronutrient mi ON mi.vitamin.idVitamin = v.idVitamin " +
			"JOIN CommonIngredient ci ON ci.micronutrient.idMicronutrient = mi.idMicronutrient " +
			"JOIN Ingredient i ON i.commonIngredient.idCommonIngredient = ci.idCommonIngredient " +
			"JOIN RecipeIngredient ri ON ri.ingredient.idIngredient = i.idIngredient " +
			"JOIN Recipe r ON r.idRecipe = ri.recipe.idRecipe " +
			"WHERE r.idRecipe = :idRecipe")
	public Object getVitaminsByRecipe(@Param("idRecipe") Long idRecipe);

	// Get vitamins by diet
	@Query("SELECT " +
			"SUM(((v.vitaminA * ri.amount) / 100) * dr.portion) AS total_a, " +
			"SUM(((vb.vitaminB1 * ri.amount) / 100) * dr.portion) AS total_b1, " +
			"SUM(((vb.vitaminB2 * ri.amount) / 100) * dr.portion) AS total_b2, " +
			"SUM(((vb.vitaminB3 * ri.amount) / 100) * dr.portion) AS total_b3, " +
			"SUM(((vb.vitaminB4 * ri.amount) / 100) * dr.portion) AS total_b4, " +
			"SUM(((vb.vitaminB5 * ri.amount) / 100) * dr.portion) AS total_b5, " +
			"SUM(((vb.vitaminB6 * ri.amount) / 100) * dr.portion) AS total_b6, " +
			"SUM(((vb.vitaminB7 * ri.amount) / 100) * dr.portion) AS total_b7, " +
			"SUM(((vb.vitaminB8 * ri.amount) / 100) * dr.portion) AS total_b8, " +
			"SUM(((vb.vitaminB9 * ri.amount) / 100) * dr.portion) AS total_b9, " +
			"SUM(((vb.vitaminB12 * ri.amount) / 100) * dr.portion) AS total_b12, " +
			"SUM(((v.vitaminC * ri.amount) / 100) * dr.portion) AS total_c, " +
			"SUM(((vd.vitaminD2 * ri.amount) / 100) * dr.portion) AS total_d2, " +
			"SUM(((vd.vitaminD3 * ri.amount) / 100) * dr.portion) AS total_d3, " +
			"SUM(((v.vitaminE * ri.amount) / 100) * dr.portion) AS total_e, " +
			"SUM(((vk.vitaminK1 * ri.amount) / 100) * dr.portion) AS total_k1, " +
			"SUM(((vk.vitaminK2 * ri.amount) / 100) * dr.portion) AS total_k2 " +
			"FROM Vitamin v " +
			"JOIN v.vitaminB vb " +
			"JOIN v.vitaminD vd " +
			"JOIN v.vitaminK vk " +
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
			"SUM(((v.vitaminA * ri.amount) / 100) * dr.portion) AS total_a, " +
			"SUM(((vb.vitaminB1 * ri.amount) / 100) * dr.portion) AS total_b1, " +
			"SUM(((vb.vitaminB2 * ri.amount) / 100) * dr.portion) AS total_b2, " +
			"SUM(((vb.vitaminB3 * ri.amount) / 100) * dr.portion) AS total_b3, " +
			"SUM(((vb.vitaminB4 * ri.amount) / 100) * dr.portion) AS total_b4, " +
			"SUM(((vb.vitaminB5 * ri.amount) / 100) * dr.portion) AS total_b5, " +
			"SUM(((vb.vitaminB6 * ri.amount) / 100) * dr.portion) AS total_b6, " +
			"SUM(((vb.vitaminB7 * ri.amount) / 100) * dr.portion) AS total_b7, " +
			"SUM(((vb.vitaminB8 * ri.amount) / 100) * dr.portion) AS total_b8, " +
			"SUM(((vb.vitaminB9 * ri.amount) / 100) * dr.portion) AS total_b9, " +
			"SUM(((vb.vitaminB12 * ri.amount) / 100) * dr.portion) AS total_b12, " +
			"SUM(((v.vitaminC * ri.amount) / 100) * dr.portion) AS total_c, " +
			"SUM(((vd.vitaminD2 * ri.amount) / 100) * dr.portion) AS total_d2, " +
			"SUM(((vd.vitaminD3 * ri.amount) / 100) * dr.portion) AS total_d3, " +
			"SUM(((v.vitaminE * ri.amount) / 100) * dr.portion) AS total_e, " +
			"SUM(((vk.vitaminK1 * ri.amount) / 100) * dr.portion) AS total_k1, " +
			"SUM(((vk.vitaminK2 * ri.amount) / 100) * dr.portion) AS total_k2 " +
			"FROM Vitamin v " +
			"JOIN v.vitaminB vb " +
			"JOIN v.vitaminD vd " +
			"JOIN v.vitaminK vk " +
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
