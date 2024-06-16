package com.evenjoin.diet_ms.repository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.evenjoin.diet_ms.entity.Diet;

import feign.Param;

public interface DietRepo extends JpaRepository<Diet, Long> {

    // Get total price by diet
    @Query("SELECT " +
            "SUM(ri.amount * i.price / i.grMlPza * dr.portion) AS total_price " +
            "FROM Ingredient i " +
            "JOIN RecipeIngredient ri ON ri.ingredient.idIngredient = i.idIngredient " +
            "JOIN Recipe r ON r.idRecipe = ri.recipe.idRecipe " +
            "JOIN DietRecipe dr ON dr.recipe.idRecipe = r.idRecipe " +
            "JOIN Diet d ON d.idDiet = dr.diet.idDiet " +
            "WHERE d.idDiet = :idDiet")
    public BigDecimal getPriceByDiet(@Param("idDiet") Long idDiet);

    // Get total price by diet between dates
    @Query("SELECT " +
            "SUM(ri.amount * i.price / i.grMlPza * dr.portion) AS total_price " +
            "FROM Ingredient i " +
            "JOIN RecipeIngredient ri ON ri.ingredient.idIngredient = i.idIngredient " +
            "JOIN Recipe r ON r.idRecipe = ri.recipe.idRecipe " +
            "JOIN DietRecipe dr ON dr.recipe.idRecipe = r.idRecipe " +
            "JOIN Diet d ON d.idDiet = dr.diet.idDiet " +
            "WHERE d.date BETWEEN :startDate AND :endDate")
    public BigDecimal getPriceByDietRange(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

    // Get prices by diet between dates
    @Query("SELECT " +
            "dr.recipe.idRecipe, " +
            "SUM(ri.amount * i.price / i.grMlPza * dr.portion) AS total_price " +
            "FROM Ingredient i " +
            "JOIN RecipeIngredient ri ON ri.ingredient.idIngredient = i.idIngredient " +
            "JOIN Recipe r ON r.idRecipe = ri.recipe.idRecipe " +
            "JOIN DietRecipe dr ON dr.recipe.idRecipe = r.idRecipe " +
            "JOIN Diet d ON d.idDiet = dr.diet.idDiet " +
            "WHERE d.date BETWEEN :startDate AND :endDate " +
            "GROUP BY dr.recipe.idRecipe")
    public List<Object> getPricesByDietRange(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

    // Get total time by diet
    @Query("SELECT SUM(CASE " +
            "WHEN dr.portion = 1 THEN r.timeMinute " +
            "WHEN dr.portion = 2 THEN r.timeMinute + (r.timeMinute * .20 * (dr.portion - 1)) " +
            "WHEN dr.portion = 3 THEN r.timeMinute + (r.timeMinute * .40 * (dr.portion - 2)) " +
            "WHEN dr.portion = 4 THEN r.timeMinute + (r.timeMinute * .60 * (dr.portion - 3)) " +
            "WHEN dr.portion = 5 THEN r.timeMinute + (r.timeMinute * .80 * (dr.portion - 4)) " +
            "ELSE 0 END) AS total_time " +
            "FROM Recipe r " +
            "JOIN DietRecipe dr ON dr.recipe.idRecipe = r.idRecipe " +
            "JOIN Diet d ON d.idDiet = dr.diet.idDiet " +
            "WHERE d.idDiet = :idDiet")
    public Integer getTimeByDiet(@Param("idDiet") Long idDiet);

}
