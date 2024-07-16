package com.evenjoin.diet_ms.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.evenjoin.diet_ms.entity.Category;

import feign.Param;

public interface CategoryRepo extends JpaRepository<Category, Long> {

	// Count categories
	@Query("SELECT COUNT(c) " +
			"FROM Category c")
	public Integer countCategories();

	// Get categories by recipe book
	@Query("SELECT c " +
			"FROM Category c " +
			"JOIN c.recipeBook rb " +
			"WHERE rb.idRecipeBook = :idRecipeBook")
	public List<Category> getCategoriesByRecipeBook(@Param("idRecipeBook") Long idRecipeBook);

	// Get maximum comsumption category
	@Query("SELECT c " +
			"FROM Category c " +
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
	public Category getMaxConsumptionCategory();

	// Get minimum comsumption category
	@Query("SELECT c " +
			"FROM Category c " +
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
	public Category getMinConsumptionCategory();

}
