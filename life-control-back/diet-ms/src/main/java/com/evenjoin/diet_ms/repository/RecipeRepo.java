package com.evenjoin.diet_ms.repository;

import java.math.BigDecimal;
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
                        "SUM(ma.trans * ri.amount / 100) AS total_trans, " +
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
                        "WHEN :macronutrient = 'trans' THEN SUM(ma.trans * ri.amount / 100) " +
                        "WHEN :macronutrient = 'fiber' THEN SUM(ma.fiber * ri.amount / 100) " +
                        "WHEN :macronutrient = 'sodium' THEN SUM(ma.sodium * ri.amount / 100) " +
                        "ELSE 0 END DESC " +
                        "LIMIT 1")
        public Recipe getRecipeWithMaxMacronutrient(@Param("macronutrient") String macronutrient);

        // Get recipe with minimum nutrient
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
                        "WHEN :macronutrient = 'trans' THEN SUM(ma.trans * ri.amount / 100) " +
                        "WHEN :macronutrient = 'fiber' THEN SUM(ma.fiber * ri.amount / 100) " +
                        "WHEN :macronutrient = 'sodium' THEN SUM(ma.sodium * ri.amount / 100) " +
                        "ELSE 0 END ASC " +
                        "LIMIT 1")
        public Recipe getRecipeWithMinMacronutrient(@Param("macronutrient") String macronutrient);

        // Get price by recipe
        @Query("SELECT SUM(ri.amount * i.price / i.grMlPza) AS total_price " +
                        "FROM Ingredient i " +
                        "JOIN RecipeIngredient ri ON ri.ingredient.idIngredient = i.idIngredient " +
                        "JOIN Recipe r ON r.idRecipe = ri.recipe.idRecipe " +
                        "WHERE r.idRecipe = :idRecipe")
        public BigDecimal getPriceByRecipe(@Param("idRecipe") Long idRecipe);
}
