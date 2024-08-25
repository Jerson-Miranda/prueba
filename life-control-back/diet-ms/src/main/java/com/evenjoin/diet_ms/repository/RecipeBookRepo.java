package com.evenjoin.diet_ms.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.evenjoin.diet_ms.entity.RecipeBook;

import feign.Param;

public interface RecipeBookRepo extends JpaRepository<RecipeBook, Long> {

        // Count recipe books
        @Query("SELECT COUNT(rb) " +
                        "FROM RecipeBook rb")
        public Integer countRecipeBooks();

        // Get recipe book by recipe
        @Query("SELECT rb " +
                        "FROM RecipeBook rb " +
                        "JOIN Category c ON c.recipeBook.idRecipeBook = rb.idRecipeBook " +
                        "JOIN Subcategory sc ON sc.category.idCategory = c.idCategory " +
                        "JOIN Recipe r ON r.subcategory.idSubcategory = sc.idSubcategory " +
                        "WHERE r.idRecipe = :idRecipe")
        public RecipeBook getRecipeBookByRecipe(@Param("idRecipe") Long idRecipe);

        // Get favorite recipe books
        @Query("SELECT rb " +
                        "FROM RecipeBook rb " +
                        "WHERE rb.isFavorite = true")
        public List<RecipeBook> getFavoriteRecipeBooks();

        // Get maximum comsumption recipe book
        @Query("SELECT rb " +
                        "FROM RecipeBook rb " +
                        "JOIN Category c ON c.recipeBook.idRecipeBook = rb.idRecipeBook " +
                        "JOIN Subcategory sc ON sc.category.idCategory = c.idCategory " +
                        "JOIN CommonIngredient ci ON ci.subcategory.idSubcategory = sc.idSubcategory " +
                        "JOIN Ingredient i ON i.commonIngredient.idCommonIngredient = ci.idCommonIngredient " +
                        "JOIN RecipeIngredient ri ON ri.ingredient.idIngredient = i.idIngredient " +
                        "JOIN Recipe r ON r.idRecipe = ri.recipe.idRecipe " +
                        "JOIN DietRecipe dr ON dr.recipe.idRecipe = r.idRecipe " +
                        "JOIN Diet d ON d.idDiet = dr.diet.idDiet " +
                        "JOIN ScheduleDiet sd ON sd.diet.idDiet = d.idDiet " +
                        "WHERE sd.isChecked = true " +
                        "GROUP BY c.idCategory " +
                        "ORDER BY COUNT(sd.isChecked) DESC")
        public RecipeBook getMaxConsumptionRecipeBook();

        // Get minimum comsumption recipe book
        @Query("SELECT rb " +
                        "FROM RecipeBook rb " +
                        "JOIN Category c ON c.recipeBook.idRecipeBook = rb.idRecipeBook " +
                        "JOIN Subcategory sc ON sc.category.idCategory = c.idCategory " +
                        "JOIN CommonIngredient ci ON ci.subcategory.idSubcategory = sc.idSubcategory " +
                        "JOIN Ingredient i ON i.commonIngredient.idCommonIngredient = ci.idCommonIngredient " +
                        "JOIN RecipeIngredient ri ON ri.ingredient.idIngredient = i.idIngredient " +
                        "JOIN Recipe r ON r.idRecipe = ri.recipe.idRecipe " +
                        "JOIN DietRecipe dr ON dr.recipe.idRecipe = r.idRecipe " +
                        "JOIN Diet d ON d.idDiet = dr.diet.idDiet " +
                        "JOIN ScheduleDiet sd ON sd.diet.idDiet = d.idDiet " +
                        "WHERE sd.isChecked = true " +
                        "GROUP BY c.idCategory " +
                        "ORDER BY COUNT(sd.isChecked) ASC")
        public RecipeBook getMinConsumptionRecipeBook();

}
