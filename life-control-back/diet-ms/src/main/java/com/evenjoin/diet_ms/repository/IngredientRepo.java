package com.evenjoin.diet_ms.repository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.evenjoin.diet_ms.entity.Ingredient;
import feign.Param;

public interface IngredientRepo extends JpaRepository<Ingredient, Long> {

	// Count ingredients
	@Query("SELECT COUNT(i) " +
			"FROM Ingredient i")
	public Integer countIngredients();

	// Get ingredients by subcategory
	@Query("SELECT i " +
			"FROM Ingredient i " +
			"JOIN i.commonIngredient ci " +
			"JOIN ci.subcategory sc " +
			"WHERE sc.idSubcategory = :idSubcategory")
	public List<Ingredient> getIngredientsBySubcategory(@Param("idSubcategory") Long idSubcategory);

	// Get ingredients by category
	@Query("SELECT i " +
			"FROM Ingredient i " +
			"JOIN i.commonIngredient ci " +
			"JOIN ci.subcategory sc " +
			"JOIN sc.category c " +
			"WHERE c.idCategory = :idCategory")
	public List<Ingredient> getIngredientsByCategory(@Param("idCategory") Long idCategory);

	// Get ingredient with maximum nutrient
	@Query("SELECT i " +
			"FROM Ingredient i " +
			"JOIN i.commonIngredient ci " +
			"JOIN ci.macronutrient ma " +
			"ORDER BY CASE " +
			"WHEN :nutrient = 'kcal' THEN ma.kcal " +
			"WHEN :nutrient = 'protein' THEN ma.protein " +
			"WHEN :nutrient = 'carbohydrate' THEN ma.carbohydrate " +
			"WHEN :nutrient = 'sugar' THEN ma.sugar " +
			"WHEN :nutrient = 'addedSugar' THEN ma.addedSugar " +
			"WHEN :nutrient = 'fat' THEN ma.fat " +
			"WHEN :nutrient = 'saturatedFat' THEN ma.saturatedFat " +
			"WHEN :nutrient = 'transFat' THEN ma.transFat " +
			"WHEN :nutrient = 'fiber' THEN ma.fiber " +
			"WHEN :nutrient = 'sodium' THEN ma.sodium " +
			"ELSE 0 END DESC " +
			"LIMIT 1")
	public Ingredient getIngredientWithMaxNutrient(@Param("nutrient") String nutrient);

	// Get ingredient with minimum nutrient
	@Query("SELECT i " +
			"FROM Ingredient i " +
			"JOIN i.commonIngredient ci " +
			"JOIN ci.macronutrient ma " +
			"ORDER BY CASE " +
			"WHEN :nutrient = 'kcal' THEN ma.kcal " +
			"WHEN :nutrient = 'protein' THEN ma.protein " +
			"WHEN :nutrient = 'carbohydrate' THEN ma.carbohydrate " +
			"WHEN :nutrient = 'sugar' THEN ma.sugar " +
			"WHEN :nutrient = 'addedSugar' THEN ma.addedSugar " +
			"WHEN :nutrient = 'fat' THEN ma.fat " +
			"WHEN :nutrient = 'saturatedFat' THEN ma.saturatedFat " +
			"WHEN :nutrient = 'transFat' THEN ma.transFat " +
			"WHEN :nutrient = 'fiber' THEN ma.fiber " +
			"WHEN :nutrient = 'sodium' THEN ma.sodium " +
			"ELSE 0 END ASC " +
			"LIMIT 1")
	public Ingredient getIngredientWithMinNutrient(@Param("nutrient") String nutrient);

	// Get quantity to consume by ingredient
	@Query("SELECT ma.portion " +
			"FROM Macronutrient ma " +
			"JOIN CommonIngredient ci ON ci.macronutrient.idMacronutrient = ma.idMacronutrient " +
			"JOIN Ingredient i ON i.commonIngredient.idCommonIngredient = ci.idCommonIngredient " +
			"WHERE i.barcode = :barcode")
	public BigDecimal getQuantityToConsume(@Param("barcode") String barcode);

	// Get ingredients by recipe
	@Query("SELECT i " +
			"FROM Ingredient i " +
			"JOIN RecipeIngredient ri ON i.idIngredient = ri.ingredient.idIngredient " +
			"JOIN Recipe r ON ri.recipe.idRecipe = r.idRecipe " +
			"WHERE r.idRecipe = :idRecipe")
	public List<Ingredient> getIngredientsByRecipe(@Param("idRecipe") Long idRecipe);

	// Get ingredients by minimum stock
	@Query("SELECT i " +
			"FROM Ingredient i " +
			"JOIN ( " +
			"SELECT i.idIngredient AS idIngredient, SUM(p.stock) AS totalStock " +
			"FROM Ingredient i " +
			"JOIN Pantry p ON p.ingredient.idIngredient = i.idIngredient " +
			"GROUP BY i.idIngredient " +
			") data ON i.idIngredient = data.idIngredient " +
			"WHERE data.totalStock <= :stock")
	public List<Ingredient> getIngredientsByMinStock(@Param("stock") Integer stock);

	// Get favorite ingredients
	@Query("SELECT i " +
			"FROM Ingredient i " +
			"WHERE i.isFavorite = true")
	public List<Ingredient> getFavoriteIngredients();

	// Get ingredients by diet
	@Query("SELECT i.idIngredient, i.barcode, ci.name, ci.brand, i.grMlPza, " +
			"SUM(ri.amount * dr.portion) AS amount, " +
			"SUM(ri.amount * i.price / i.grMlPza * dr.portion) AS price " +
			"FROM CommonIngredient ci " +
			"JOIN Ingredient i ON i.commonIngredient.idCommonIngredient = ci.idCommonIngredient " +
			"JOIN RecipeIngredient ri ON ri.ingredient.idIngredient = i.idIngredient " +
			"JOIN Recipe r ON r.idRecipe = ri.recipe.idRecipe " +
			"JOIN DietRecipe dr ON dr.recipe.idRecipe = r.idRecipe " +
			"JOIN Diet d ON d.idDiet = dr.diet.idDiet " +
			"WHERE d.idDiet = :idDiet " +
			"GROUP BY i.idIngredient")
	public List<Object> getIngredientsByDiet(@Param("idDiet") Long idDiet);

	// Get ingredients by diet between dates
	@Query("SELECT i.idIngredient, i.barcode, ci.name, ci.brand, i.grMlPza, " +
			"SUM(ri.amount * dr.portion) AS amount, " +
			"SUM(ri.amount * i.price / i.grMlPza * dr.portion) AS price " +
			"FROM CommonIngredient ci " +
			"JOIN Ingredient i ON i.commonIngredient.idCommonIngredient = ci.idCommonIngredient " +
			"JOIN RecipeIngredient ri ON ri.ingredient.idIngredient = i.idIngredient " +
			"JOIN Recipe r ON r.idRecipe = ri.recipe.idRecipe " +
			"JOIN DietRecipe dr ON dr.recipe.idRecipe = r.idRecipe " +
			"JOIN Diet d ON d.idDiet = dr.diet.idDiet " +
			"WHERE d.date BETWEEN :startDate AND :endDate " +
			"GROUP BY i.idIngredient")
	public List<Object> getIngredientsByDietRange(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

	// Get maximum comsumption ingredient
	@Query("SELECT i " +
			"FROM Ingredient i " +
			"JOIN RecipeIngredient ri ON ri.ingredient.idIngredient = i.idIngredient " +
			"JOIN Recipe r ON r.idRecipe = ri.recipe.idRecipe " +
			"JOIN DietRecipe dr ON dr.recipe.idRecipe = r.idRecipe " +
			"JOIN Diet d ON d.idDiet = dr.diet.idDiet " +
			"JOIN ScheduleDiet sd ON sd.diet.idDiet = d.idDiet " +
			"WHERE sd.isChecked = true " +
			"GROUP BY i.idIngredient " +
			"ORDER BY COUNT(sd.isChecked) DESC " +
			"LIMIT 1")
	public Ingredient getMaxConsumptionIngredient();

	// Get minimum comsumption ingredient
	@Query("SELECT i " +
			"FROM Ingredient i " +
			"JOIN RecipeIngredient ri ON ri.ingredient.idIngredient = i.idIngredient " +
			"JOIN Recipe r ON r.idRecipe = ri.recipe.idRecipe " +
			"JOIN DietRecipe dr ON dr.recipe.idRecipe = r.idRecipe " +
			"JOIN Diet d ON d.idDiet = dr.diet.idDiet " +
			"JOIN ScheduleDiet sd ON sd.diet.idDiet = d.idDiet " +
			"WHERE sd.isChecked = true " +
			"GROUP BY i.idIngredient " +
			"ORDER BY COUNT(sd.isChecked) ASC " +
			"LIMIT 1")
	public Ingredient getMinConsumptionIngredient();
}
