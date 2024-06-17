package com.evenjoin.diet_ms.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.evenjoin.diet_ms.entity.Mineral;

import feign.Param;

public interface MineralRepo extends JpaRepository<Mineral, Long> {

	// Get minerals by ingredient
	@Query("SELECT m " +
			"FROM Mineral m " +
			"JOIN Micronutrient mi ON mi.mineral.idMineral = m.idMineral " +
			"JOIN CommonIngredient ci ON ci.micronutrient.idMicronutrient = mi.idMicronutrient " +
			"JOIN Ingredient i ON i.commonIngredient.idCommonIngredient = ci.idCommonIngredient " +
			"WHERE i.idIngredient = :idIngredient")
	public Mineral getMineralsByIngredient(@Param("idIngredient") Long idIngredient);
	
	// Get minerals by recipe
	@Query("SELECT " +
			"SUM((m.calcium * ri.amount) / 100) AS total_calcium, " +
			"SUM((m.phosphorus * ri.amount) / 100) AS total_phosphorus, " +
			"SUM((m.potassium * ri.amount) / 100) AS total_potassium, " +
			"SUM((m.sodium * ri.amount) / 100) AS total_sodium, " +
			"SUM((m.chloride * ri.amount) / 100) AS total_chloride, " +
			"SUM((m.magnesium * ri.amount) / 100) AS total_magnesium, " +
			"SUM((m.sulfur * ri.amount) / 100) AS total_sulfur, " +
			"SUM((m.iron * ri.amount) / 100) AS total_iron, " +
			"SUM((m.zinc * ri.amount) / 100) AS total_zinc, " +
			"SUM((m.copper * ri.amount) / 100) AS total_copper, " +
			"SUM((m.manganese * ri.amount) / 100) AS total_manganese, " +
			"SUM((m.iodine * ri.amount) / 100) AS total_iodine, " +
			"SUM((m.selenium * ri.amount) / 100) AS total_selenium, " +
			"SUM((m.molybdenum * ri.amount) / 100) AS total_molybdenum, " +
			"SUM((m.cobalt * ri.amount) / 100) AS total_cobalt, " +
			"SUM((m.fluoride * ri.amount) / 100) AS total_fluoride, " +
			"SUM((m.chromium * ri.amount) / 100) AS total_chromium " +
			"FROM Mineral m " +
			"JOIN Micronutrient mi ON mi.mineral.idMineral = m.idMineral " +
			"JOIN CommonIngredient ci ON ci.micronutrient.idMicronutrient = mi.idMicronutrient " +
			"JOIN Ingredient i ON i.commonIngredient.idCommonIngredient = ci.idCommonIngredient " +
			"JOIN RecipeIngredient ri ON ri.ingredient.idIngredient = i.idIngredient " +
			"JOIN Recipe r ON r.idRecipe = ri.recipe.idRecipe " +
			"WHERE r.idRecipe = :idRecipe")
	public Object getMineralsByRecipe(@Param("idRecipe") Long idRecipe);

	// Get minerals by diet
	@Query("SELECT " +
			"SUM((m.calcium * ri.amount / 100) * dr.portion) AS total_calcium, " +
			"SUM((m.phosphorus * ri.amount / 100) * dr.portion) AS total_phosphorus, " +
			"SUM((m.potassium * ri.amount / 100) * dr.portion) AS total_potassium, " +
			"SUM((m.sodium * ri.amount / 100) * dr.portion) AS total_sodium, " +
			"SUM((m.chloride * ri.amount / 100) * dr.portion) AS total_chloride, " +
			"SUM((m.magnesium * ri.amount / 100) * dr.portion) AS total_magnesium, " +
			"SUM((m.sulfur * ri.amount / 100) * dr.portion) AS total_sulfur, " +
			"SUM((m.iron * ri.amount / 100) * dr.portion) AS total_iron, " +
			"SUM((m.zinc * ri.amount / 100) * dr.portion) AS total_zinc, " +
			"SUM((m.copper * ri.amount / 100) * dr.portion) AS total_copper, " +
			"SUM((m.manganese * ri.amount / 100) * dr.portion) AS total_manganese, " +
			"SUM((m.iodine * ri.amount / 100) * dr.portion) AS total_iodine, " +
			"SUM((m.selenium * ri.amount / 100) * dr.portion) AS total_selenium, " +
			"SUM((m.molybdenum * ri.amount / 100) * dr.portion) AS total_molybdenum, " +
			"SUM((m.cobalt * ri.amount / 100) * dr.portion) AS total_cobalt, " +
			"SUM((m.fluoride * ri.amount / 100) * dr.portion) AS total_fluoride, " +
			"SUM((m.chromium * ri.amount / 100) * dr.portion) AS total_chromium " +
			"FROM Mineral m " +
			"JOIN Micronutrient mi ON mi.mineral.idMineral = m.idMineral " +
			"JOIN CommonIngredient ci ON ci.micronutrient.idMicronutrient = mi.idMicronutrient " +
			"JOIN Ingredient i ON i.commonIngredient.idCommonIngredient = ci.idCommonIngredient " +
			"JOIN RecipeIngredient ri ON ri.ingredient.idIngredient = i.idIngredient " +
			"JOIN Recipe r ON r.idRecipe = ri.recipe.idRecipe " +
			"JOIN DietRecipe dr ON dr.recipe.idRecipe = r.idRecipe " +
			"JOIN Diet d ON d.idDiet = dr.diet.idDiet " +
			"WHERE d.idDiet = :idDiet")
	public Object getMineralsByDiet(@Param("idDiet") Long idDiet);

	// Get minerals by diet between dates
	@Query("SELECT d.idDiet, d.date, " +
			"SUM((m.calcium * ri.amount / 100) * dr.portion) AS total_calcium, " +
			"SUM((m.phosphorus * ri.amount / 100) * dr.portion) AS total_phosphorus, " +
			"SUM((m.potassium * ri.amount / 100) * dr.portion) AS total_potassium, " +
			"SUM((m.sodium * ri.amount / 100) * dr.portion) AS total_sodium, " +
			"SUM((m.chloride * ri.amount / 100) * dr.portion) AS total_chloride, " +
			"SUM((m.magnesium * ri.amount / 100) * dr.portion) AS total_magnesium, " +
			"SUM((m.sulfur * ri.amount / 100) * dr.portion) AS total_sulfur, " +
			"SUM((m.iron * ri.amount / 100) * dr.portion) AS total_iron, " +
			"SUM((m.zinc * ri.amount / 100) * dr.portion) AS total_zinc, " +
			"SUM((m.copper * ri.amount / 100) * dr.portion) AS total_copper, " +
			"SUM((m.manganese * ri.amount / 100) * dr.portion) AS total_manganese, " +
			"SUM((m.iodine * ri.amount / 100) * dr.portion) AS total_iodine, " +
			"SUM((m.selenium * ri.amount / 100) * dr.portion) AS total_selenium, " +
			"SUM((m.molybdenum * ri.amount / 100) * dr.portion) AS total_molybdenum, " +
			"SUM((m.cobalt * ri.amount / 100) * dr.portion) AS total_cobalt, " +
			"SUM((m.fluoride * ri.amount / 100) * dr.portion) AS total_fluoride, " +
			"SUM((m.chromium * ri.amount / 100) * dr.portion) AS total_chromium " +
			"FROM Mineral m " +
			"JOIN Micronutrient mi ON mi.mineral.idMineral = m.idMineral " +
			"JOIN CommonIngredient ci ON ci.micronutrient.idMicronutrient = mi.idMicronutrient " +
			"JOIN Ingredient i ON i.commonIngredient.idCommonIngredient = ci.idCommonIngredient " +
			"JOIN RecipeIngredient ri ON ri.ingredient.idIngredient = i.idIngredient " +
			"JOIN Recipe r ON r.idRecipe = ri.recipe.idRecipe " +
			"JOIN DietRecipe dr ON dr.recipe.idRecipe = r.idRecipe " +
			"JOIN Diet d ON d.idDiet = dr.diet.idDiet " +
			"WHERE d.date BETWEEN :startDate AND :endDate " +
			"GROUP BY d.idDiet")
	public List<Object> getMineralsByDietRange(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

}
