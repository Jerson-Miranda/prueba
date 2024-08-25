package com.evenjoin.diet_ms.repository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.evenjoin.diet_ms.entity.Recipe;
import feign.Param;

public interface RecipeRepo extends JpaRepository<Recipe, Long> {

        // Count recipes
        @Query("SELECT COUNT(r) " +
                        "FROM Recipe r")
        public Integer countRecipes();

        // Count recipes by recipe book
        @Query("SELECT COUNT(r) " +
                        "FROM Recipe r " +
                        "JOIN r.subcategory sc " +
                        "JOIN sc.category c " +
                        "JOIN c.recipeBook rb " +
                        "WHERE rb.idRecipeBook = :idRecipeBook")
        public Integer countRecipesByRecipeBook(@Param("idRecipeBook") Long idRecipeBook);

        // Get recipes by recipe book
        @Query("SELECT r " +
                        "FROM Recipe r " +
                        "JOIN r.subcategory sc " +
                        "JOIN sc.category c " +
                        "JOIN c.recipeBook rb " +
                        "WHERE rb.idRecipeBook = :idRecipeBook")
        public List<Recipe> getRecipesByRecipeBook(@Param("idRecipeBook") Long idRecipeBook);

        // Get recipes by subcategory
        @Query("SELECT r " +
                        "FROM Recipe r " +
                        "JOIN r.subcategory sc " +
                        "WHERE sc.idSubcategory = :idSubcategory")
        public List<Recipe> getRecipesBySubcategory(@Param("idSubcategory") Long idSubcategory);

        // Get recipes by category
        @Query("SELECT r " +
                        "FROM Recipe r " +
                        "JOIN r.subcategory sc " +
                        "JOIN sc.category c " +
                        "WHERE c.idCategory = :idCategory")
        public List<Recipe> getRecipesByCategory(@Param("idCategory") Long idCategory);

        // Get recipes by ingredient
        @Query("SELECT r " +
                        "FROM Recipe r " +
                        "JOIN RecipeIngredient ri ON r.idRecipe = ri.recipe.idRecipe " +
                        "JOIN Ingredient i ON i.idIngredient = ri.ingredient.idIngredient " +
                        "WHERE i.idIngredient = :idIngredient")
        public List<Recipe> getRecipesByIngredient(@Param("idIngredient") Long idIngredient);

        // Get macronutrients by recipe
        @Query("SELECT " +
                        "SUM(ma.kcal * ri.amount / 100) AS total_kcal, " +
                        "SUM(ma.protein * ri.amount / 100) AS total_protein, " +
                        "SUM(ma.carbohydrate * ri.amount / 100) AS total_carbohydrate, " +
                        "SUM(ma.sugar * ri.amount / 100) AS total_sugar, " +
                        "SUM(ma.addedSugar * ri.amount / 100) AS total_addedSugar, " +
                        "SUM(ma.fat * ri.amount / 100) AS total_fat, " +
                        "SUM(ma.saturatedFat * ri.amount / 100) AS total_saturatedFat, " +
                        "SUM(ma.transFat * ri.amount / 100) AS total_transFat, " +
                        "SUM(ma.fiber * ri.amount / 100) AS total_fiber, " +
                        "SUM(ma.sodium * ri.amount / 100) AS total_sodium " +
                        "FROM Macronutrient ma " +
                        "JOIN CommonIngredient ci ON ci.macronutrient.idMacronutrient = ma.idMacronutrient " +
                        "JOIN Ingredient i ON i.commonIngredient.idCommonIngredient = ci.idCommonIngredient " +
                        "JOIN RecipeIngredient ri ON ri.ingredient.idIngredient = i.idIngredient " +
                        "JOIN Recipe r ON r.idRecipe = ri.recipe.idRecipe " +
                        "WHERE r.idRecipe = :idRecipe")
        public Object getMacronutrientsByRecipe(@Param("idRecipe") Long idRecipe);

        // Get recipe with maximum macronutrient
        @Query("SELECT r " +
                        "FROM Recipe r " +
                        "JOIN RecipeIngredient ri ON ri.recipe.idRecipe = r.idRecipe " +
                        "JOIN Ingredient i ON i.idIngredient = ri.ingredient.idIngredient " +
                        "JOIN i.commonIngredient ci " +
                        "JOIN ci.macronutrient ma " +
                        "GROUP BY r.idRecipe " +
                        "ORDER BY CASE " +
                        "WHEN :macronutrient = 'kcal' THEN SUM(ma.kcal * ri.amount / 100) " +
                        "WHEN :macronutrient = 'protein' THEN SUM(ma.protein * ri.amount / 100) " +
                        "WHEN :macronutrient = 'carbohydrate' THEN SUM(ma.carbohydrate * ri.amount / 100) " +
                        "WHEN :macronutrient = 'sugar' THEN SUM(ma.sugar * ri.amount / 100) " +
                        "WHEN :macronutrient = 'addedSugar' THEN SUM(ma.addedSugar * ri.amount / 100) " +
                        "WHEN :macronutrient = 'fat' THEN SUM(ma.fat * ri.amount / 100) " +
                        "WHEN :macronutrient = 'saturatedFat' THEN SUM(ma.saturatedFat * ri.amount / 100) " +
                        "WHEN :macronutrient = 'transFat' THEN SUM(ma.transFat * ri.amount / 100) " +
                        "WHEN :macronutrient = 'fiber' THEN SUM(ma.fiber * ri.amount / 100) " +
                        "WHEN :macronutrient = 'sodium' THEN SUM(ma.sodium * ri.amount / 100) " +
                        "ELSE 0 END DESC " +
                        "LIMIT 1")
        public Recipe getRecipeWithMaxMacronutrient(@Param("macronutrient") String macronutrient);

        // Get recipe with minimum macronutrient
        @Query("SELECT r " +
                        "FROM Recipe r " +
                        "JOIN RecipeIngredient ri ON ri.recipe.idRecipe = r.idRecipe " +
                        "JOIN Ingredient i ON i.idIngredient = ri.ingredient.idIngredient " +
                        "JOIN i.commonIngredient ci " +
                        "JOIN ci.macronutrient ma " +
                        "GROUP BY r.idRecipe " +
                        "ORDER BY CASE " +
                        "WHEN :macronutrient = 'kcal' THEN SUM(ma.kcal * ri.amount / 100) " +
                        "WHEN :macronutrient = 'protein' THEN SUM(ma.protein * ri.amount / 100) " +
                        "WHEN :macronutrient = 'carbohydrate' THEN SUM(ma.carbohydrate * ri.amount / 100) " +
                        "WHEN :macronutrient = 'sugar' THEN SUM(ma.sugar * ri.amount / 100) " +
                        "WHEN :macronutrient = 'addedSugar' THEN SUM(ma.addedSugar * ri.amount / 100) " +
                        "WHEN :macronutrient = 'fat' THEN SUM(ma.fat * ri.amount / 100) " +
                        "WHEN :macronutrient = 'saturatedFat' THEN SUM(ma.saturatedFat * ri.amount / 100) " +
                        "WHEN :macronutrient = 'transFat' THEN SUM(ma.transFat * ri.amount / 100) " +
                        "WHEN :macronutrient = 'fiber' THEN SUM(ma.fiber * ri.amount / 100) " +
                        "WHEN :macronutrient = 'sodium' THEN SUM(ma.sodium * ri.amount / 100) " +
                        "ELSE 0 END ASC " +
                        "LIMIT 1")
        public Recipe getRecipeWithMinMacronutrient(@Param("macronutrient") String macronutrient);

        // Get vitamins by recipe
        @Query("SELECT " +
                        "SUM(v.vitaminA * ri.amount / 100) AS total_a, " +
                        "SUM(vb.vitaminB1 * ri.amount / 100) AS total_b1, " +
                        "SUM(vb.vitaminB2 * ri.amount / 100) AS total_b2, " +
                        "SUM(vb.vitaminB3 * ri.amount / 100) AS total_b3, " +
                        "SUM(vb.vitaminB4 * ri.amount / 100) AS total_b4, " +
                        "SUM(vb.vitaminB5 * ri.amount / 100) AS total_b5, " +
                        "SUM(vb.vitaminB6 * ri.amount / 100) AS total_b6, " +
                        "SUM(vb.vitaminB7 * ri.amount / 100) AS total_b7, " +
                        "SUM(vb.vitaminB8 * ri.amount / 100) AS total_b8, " +
                        "SUM(vb.vitaminB9 * ri.amount / 100) AS total_b9, " +
                        "SUM(vb.vitaminB12 * ri.amount / 100) AS total_b12, " +
                        "SUM(v.vitaminC * ri.amount / 100) AS total_c, " +
                        "SUM(vd.vitaminD2 * ri.amount / 100) AS total_d2, " +
                        "SUM(vd.vitaminD3 * ri.amount / 100) AS total_d3, " +
                        "SUM(v.vitaminE * ri.amount / 100) AS total_e, " +
                        "SUM(vk.vitaminK1 * ri.amount / 100) AS total_k1, " +
                        "SUM(vk.vitaminK2 * ri.amount / 100) AS total_k2 " +
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

        // Get recipe with maximum vitamin
        @Query("SELECT r " +
                        "FROM Recipe r " +
                        "JOIN RecipeIngredient ri ON ri.recipe.idRecipe = r.idRecipe " +
                        "JOIN Ingredient i ON i.idIngredient = ri.ingredient.idIngredient " +
                        "JOIN i.commonIngredient ci " +
                        "JOIN ci.micronutrient mi " +
                        "JOIN mi.vitamin v " +
                        "JOIN v.vitaminB vb " +
                        "JOIN v.vitaminD vd " +
                        "JOIN v.vitaminK vk " +
                        "GROUP BY r.idRecipe " +
                        "ORDER BY CASE " +
                        "WHEN :vitamin = 'a' THEN SUM(v.vitaminA * ri.amount / 100) " +
                        "WHEN :vitamin = 'b1' THEN SUM(vb.vitaminB1 * ri.amount / 100) " +
                        "WHEN :vitamin = 'b2' THEN SUM(vb.vitaminB2 * ri.amount / 100) " +
                        "WHEN :vitamin = 'b3' THEN SUM(vb.vitaminB3 * ri.amount / 100) " +
                        "WHEN :vitamin = 'b4' THEN SUM(vb.vitaminB4 * ri.amount / 100) " +
                        "WHEN :vitamin = 'b5' THEN SUM(vb.vitaminB5 * ri.amount / 100) " +
                        "WHEN :vitamin = 'b6' THEN SUM(vb.vitaminB6 * ri.amount / 100) " +
                        "WHEN :vitamin = 'b7' THEN SUM(vb.vitaminB7 * ri.amount / 100) " +
                        "WHEN :vitamin = 'b8' THEN SUM(vb.vitaminB8 * ri.amount / 100) " +
                        "WHEN :vitamin = 'b9' THEN SUM(vb.vitaminB9 * ri.amount / 100) " +
                        "WHEN :vitamin = 'b12' THEN SUM(vb.vitaminB12 * ri.amount / 100) " +
                        "WHEN :vitamin = 'c' THEN SUM(v.vitaminC * ri.amount / 100) " +
                        "WHEN :vitamin = 'd2' THEN SUM(vd.vitaminD2 * ri.amount / 100) " +
                        "WHEN :vitamin = 'd3' THEN SUM(vd.vitaminD3 * ri.amount / 100) " +
                        "WHEN :vitamin = 'e' THEN SUM(v.vitaminE * ri.amount / 100) " +
                        "WHEN :vitamin = 'k1' THEN SUM(vk.vitaminK1 * ri.amount / 100) " +
                        "WHEN :vitamin = 'k2' THEN SUM(vk.vitaminK2 * ri.amount / 100) " +
                        "ELSE 0 END DESC " +
                        "LIMIT 1")
        public Recipe getRecipeWithMaxVitamin(@Param("vitamin") String vitamin);

        // Get recipe with minimum vitamin
        @Query("SELECT r " +
                        "FROM Recipe r " +
                        "JOIN RecipeIngredient ri ON ri.recipe.idRecipe = r.idRecipe " +
                        "JOIN Ingredient i ON i.idIngredient = ri.ingredient.idIngredient " +
                        "JOIN i.commonIngredient ci " +
                        "JOIN ci.micronutrient mi " +
                        "JOIN mi.vitamin v " +
                        "JOIN v.vitaminB vb " +
                        "JOIN v.vitaminD vd " +
                        "JOIN v.vitaminK vk " +
                        "GROUP BY r.idRecipe " +
                        "ORDER BY CASE " +
                        "WHEN :vitamin = 'a' THEN SUM(v.vitaminA * ri.amount / 100) " +
                        "WHEN :vitamin = 'b1' THEN SUM(vb.vitaminB1 * ri.amount / 100) " +
                        "WHEN :vitamin = 'b2' THEN SUM(vb.vitaminB2 * ri.amount / 100) " +
                        "WHEN :vitamin = 'b3' THEN SUM(vb.vitaminB3 * ri.amount / 100) " +
                        "WHEN :vitamin = 'b4' THEN SUM(vb.vitaminB4 * ri.amount / 100) " +
                        "WHEN :vitamin = 'b5' THEN SUM(vb.vitaminB5 * ri.amount / 100) " +
                        "WHEN :vitamin = 'b6' THEN SUM(vb.vitaminB6 * ri.amount / 100) " +
                        "WHEN :vitamin = 'b7' THEN SUM(vb.vitaminB7 * ri.amount / 100) " +
                        "WHEN :vitamin = 'b8' THEN SUM(vb.vitaminB8 * ri.amount / 100) " +
                        "WHEN :vitamin = 'b9' THEN SUM(vb.vitaminB9 * ri.amount / 100) " +
                        "WHEN :vitamin = 'b12' THEN SUM(vb.vitaminB12 * ri.amount / 100) " +
                        "WHEN :vitamin = 'c' THEN SUM(v.vitaminC * ri.amount / 100) " +
                        "WHEN :vitamin = 'd2' THEN SUM(vd.vitaminD2 * ri.amount / 100) " +
                        "WHEN :vitamin = 'd3' THEN SUM(vd.vitaminD3 * ri.amount / 100) " +
                        "WHEN :vitamin = 'e' THEN SUM(v.vitaminE * ri.amount / 100) " +
                        "WHEN :vitamin = 'k1' THEN SUM(vk.vitaminK1 * ri.amount / 100) " +
                        "WHEN :vitamin = 'k2' THEN SUM(vk.vitaminK2 * ri.amount / 100) " +
                        "ELSE 0 END ASC " +
                        "LIMIT 1")
        public Recipe getRecipeWithMinVitamin(@Param("vitamin") String vitamin);

        // Get minerals by recipe
        @Query("SELECT " +
                        "SUM(m.calcium * ri.amount / 100) AS total_calcium, " +
                        "SUM(m.phosphorus * ri.amount / 100) AS total_phosphorus, " +
                        "SUM(m.potassium * ri.amount / 100) AS total_potassium, " +
                        "SUM(m.sodium * ri.amount / 100) AS total_sodium, " +
                        "SUM(m.chloride * ri.amount / 100) AS total_chloride, " +
                        "SUM(m.magnesium * ri.amount / 100) AS total_magnesium, " +
                        "SUM(m.sulfur * ri.amount / 100) AS total_sulfur, " +
                        "SUM(m.iron * ri.amount / 100) AS total_iron, " +
                        "SUM(m.zinc * ri.amount / 100) AS total_zinc, " +
                        "SUM(m.copper * ri.amount / 100) AS total_copper, " +
                        "SUM(m.manganese * ri.amount / 100) AS total_manganese, " +
                        "SUM(m.iodine * ri.amount / 100) AS total_iodine, " +
                        "SUM(m.selenium * ri.amount / 100) AS total_selenium, " +
                        "SUM(m.molybdenum * ri.amount / 100) AS total_molybdenum, " +
                        "SUM(m.cobalt * ri.amount / 100) AS total_cobalt, " +
                        "SUM(m.fluoride * ri.amount / 100) AS total_fluoride, " +
                        "SUM(m.chromium * ri.amount / 100) AS total_chromium " +
                        "FROM Mineral m " +
                        "JOIN Micronutrient mi ON mi.mineral.idMineral = m.idMineral " +
                        "JOIN CommonIngredient ci ON ci.micronutrient.idMicronutrient = mi.idMicronutrient " +
                        "JOIN Ingredient i ON i.commonIngredient.idCommonIngredient = ci.idCommonIngredient " +
                        "JOIN RecipeIngredient ri ON ri.ingredient.idIngredient = i.idIngredient " +
                        "JOIN Recipe r ON r.idRecipe = ri.recipe.idRecipe " +
                        "WHERE r.idRecipe = :idRecipe")
        public Object getMineralsByRecipe(@Param("idRecipe") Long idRecipe);

        // Get recipe with maximum mineral
        @Query("SELECT r " +
                        "FROM Recipe r " +
                        "JOIN RecipeIngredient ri ON ri.recipe.idRecipe = r.idRecipe " +
                        "JOIN Ingredient i ON i.idIngredient = ri.ingredient.idIngredient " +
                        "JOIN i.commonIngredient ci " +
                        "JOIN ci.micronutrient mi " +
                        "JOIN mi.mineral m " +
                        "GROUP BY r.idRecipe " +
                        "ORDER BY CASE " +
                        "WHEN :mineral = 'calcium' THEN SUM(m.calcium * ri.amount / 100) " +
                        "WHEN :mineral = 'phosphorus' THEN SUM(m.phosphorus * ri.amount / 100) " +
                        "WHEN :mineral = 'potassium' THEN SUM(m.potassium * ri.amount / 100) " +
                        "WHEN :mineral = 'sodium' THEN SUM(m.sodium * ri.amount / 100) " +
                        "WHEN :mineral = 'chloride' THEN SUM(m.chloride * ri.amount / 100) " +
                        "WHEN :mineral = 'magnesium' THEN SUM(m.magnesium * ri.amount / 100) " +
                        "WHEN :mineral = 'sulfur' THEN SUM(m.sulfur * ri.amount / 100) " +
                        "WHEN :mineral = 'iron' THEN SUM(m.iron * ri.amount / 100) " +
                        "WHEN :mineral = 'zinc' THEN SUM(m.zinc * ri.amount / 100) " +
                        "WHEN :mineral = 'copper' THEN SUM(m.copper * ri.amount / 100) " +
                        "WHEN :mineral = 'manganese' THEN SUM(m.manganese * ri.amount / 100) " +
                        "WHEN :mineral = 'iodine' THEN SUM(m.iodine * ri.amount / 100) " +
                        "WHEN :mineral = 'selenium' THEN SUM(m.selenium * ri.amount / 100) " +
                        "WHEN :mineral = 'molybdenum' THEN SUM(m.molybdenum * ri.amount / 100) " +
                        "WHEN :mineral = 'cobalt' THEN SUM(m.cobalt * ri.amount / 100) " +
                        "WHEN :mineral = 'fluoride' THEN SUM(m.fluoride * ri.amount / 100) " +
                        "WHEN :mineral = 'chromium' THEN SUM(m.chromium * ri.amount / 100) " +
                        "ELSE 0 END DESC " +
                        "LIMIT 1")
        public Recipe getRecipeWithMaxMineral(@Param("mineral") String mineral);

        // Get recipe with minimum mineral
        @Query("SELECT r " +
                        "FROM Recipe r " +
                        "JOIN RecipeIngredient ri ON ri.recipe.idRecipe = r.idRecipe " +
                        "JOIN Ingredient i ON i.idIngredient = ri.ingredient.idIngredient " +
                        "JOIN i.commonIngredient ci " +
                        "JOIN ci.micronutrient mi " +
                        "JOIN mi.mineral m " +
                        "GROUP BY r.idRecipe " +
                        "ORDER BY CASE " +
                        "WHEN :mineral = 'calcium' THEN SUM(m.calcium * ri.amount / 100) " +
                        "WHEN :mineral = 'phosphorus' THEN SUM(m.phosphorus * ri.amount / 100) " +
                        "WHEN :mineral = 'potassium' THEN SUM(m.potassium * ri.amount / 100) " +
                        "WHEN :mineral = 'sodium' THEN SUM(m.sodium * ri.amount / 100) " +
                        "WHEN :mineral = 'chloride' THEN SUM(m.chloride * ri.amount / 100) " +
                        "WHEN :mineral = 'magnesium' THEN SUM(m.magnesium * ri.amount / 100) " +
                        "WHEN :mineral = 'sulfur' THEN SUM(m.sulfur * ri.amount / 100) " +
                        "WHEN :mineral = 'iron' THEN SUM(m.iron * ri.amount / 100) " +
                        "WHEN :mineral = 'zinc' THEN SUM(m.zinc * ri.amount / 100) " +
                        "WHEN :mineral = 'copper' THEN SUM(m.copper * ri.amount / 100) " +
                        "WHEN :mineral = 'manganese' THEN SUM(m.manganese * ri.amount / 100) " +
                        "WHEN :mineral = 'iodine' THEN SUM(m.iodine * ri.amount / 100) " +
                        "WHEN :mineral = 'selenium' THEN SUM(m.selenium * ri.amount / 100) " +
                        "WHEN :mineral = 'molybdenum' THEN SUM(m.molybdenum * ri.amount / 100) " +
                        "WHEN :mineral = 'cobalt' THEN SUM(m.cobalt * ri.amount / 100) " +
                        "WHEN :mineral = 'fluoride' THEN SUM(m.fluoride * ri.amount / 100) " +
                        "WHEN :mineral = 'chromium' THEN SUM(m.chromium * ri.amount / 100) " +
                        "ELSE 0 END ASC " +
                        "LIMIT 1")
        public Recipe getRecipeWithMinMineral(@Param("mineral") String mineral);

        // Get price by recipe
        @Query("SELECT SUM(ri.amount * i.price / i.grMlPza) AS total_price " +
                        "FROM Ingredient i " +
                        "JOIN RecipeIngredient ri ON ri.ingredient.idIngredient = i.idIngredient " +
                        "JOIN Recipe r ON r.idRecipe = ri.recipe.idRecipe " +
                        "WHERE r.idRecipe = :idRecipe")
        public BigDecimal getPriceByRecipe(@Param("idRecipe") Long idRecipe);

        // Get recipe with maximum price
        @Query("SELECT r " +
                        "FROM Recipe r " +
                        "JOIN RecipeIngredient ri ON ri.recipe.idRecipe = r.idRecipe " +
                        "JOIN Ingredient i ON i.idIngredient = ri.ingredient.idIngredient " +
                        "JOIN i.commonIngredient ci " +
                        "GROUP BY r.idRecipe " +
                        "ORDER BY SUM(ri.amount * i.price / i.grMlPza) DESC " +
                        "LIMIT 1")
        public Recipe getRecipeWithMaxPrice();

        // Get recipe with minimum price
        @Query("SELECT r " +
                        "FROM Recipe r " +
                        "JOIN RecipeIngredient ri ON ri.recipe.idRecipe = r.idRecipe " +
                        "JOIN Ingredient i ON i.idIngredient = ri.ingredient.idIngredient " +
                        "JOIN i.commonIngredient ci " +
                        "GROUP BY r.idRecipe " +
                        "ORDER BY SUM(ri.amount * i.price / i.grMlPza) ASC " +
                        "LIMIT 1")
        public Recipe getRecipeWithMinPrice();

        // Get recipe with maximum time
        @Query("SELECT r " +
                        "FROM Recipe r " +
                        "WHERE r.timeMinute = (" +
                        "SELECT MAX(r2.timeMinute) " +
                        "FROM Recipe r2" +
                        ")")
        public Recipe getRecipeWithMaxTime();

        // Get recipe with minimum time
        @Query("SELECT r " +
                        "FROM Recipe r " +
                        "WHERE r.timeMinute = (" +
                        "SELECT MIN(r2.timeMinute) " +
                        "FROM Recipe r2" +
                        ")")
        public Recipe getRecipeWithMinTime();

        // Get favorite recipes
        @Query("SELECT r " +
                        "FROM Recipe r " +
                        "WHERE r.isFavorite = true")
        public List<Recipe> getFavoriteRecipes();

        // Get recipes by diet
        @Query("SELECT " +
                        "dr.recipe.idRecipe, " +
                        "SUM(dr.portion) AS portion " +
                        "FROM DietRecipe dr " +
                        "JOIN Diet d ON d.idDiet = dr.diet.idDiet " +
                        "WHERE d.idDiet = :idDiet " +
                        "GROUP BY dr.recipe.idRecipe")
        public List<Object> getRecipesByDiet(@Param("idDiet") Long idDiet);

        // Get recipes by diet between dates
        @Query("SELECT " +
                        "dr.recipe.idRecipe, " +
                        "SUM(dr.portion) AS portion " +
                        "FROM DietRecipe dr " +
                        "JOIN Diet d ON d.idDiet = dr.diet.idDiet " +
                        "WHERE d.date BETWEEN :startDate AND :endDate " +
                        "GROUP BY dr.recipe.idRecipe")
        public List<Object> getRecipesByDietRange(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

        // Get maximum comsumption recipe
	@Query("SELECT r " +
			"FROM Recipe r " +
			"JOIN DietRecipe dr ON dr.recipe.idRecipe = r.idRecipe " +
			"JOIN Diet d ON d.idDiet = dr.diet.idDiet " +
			"JOIN ScheduleDiet sd ON sd.diet.idDiet = d.idDiet " +
			"WHERE sd.isChecked = true " +
			"GROUP BY c.idCategory " +
			"ORDER BY COUNT(sd.isChecked) DESC")
	public Recipe getMaxConsumptionRecipe();

	// Get minimum comsumption recipe
	@Query("SELECT r " +
			"FROM Recipe r " +
			"JOIN DietRecipe dr ON dr.recipe.idRecipe = r.idRecipe " +
			"JOIN Diet d ON d.idDiet = dr.diet.idDiet " +
			"JOIN ScheduleDiet sd ON sd.diet.idDiet = d.idDiet " +
			"WHERE sd.isChecked = true " +
			"GROUP BY c.idCategory " +
			"ORDER BY COUNT(sd.isChecked) ASC")
	public Recipe getMinConsumptionRecipe();

}
