package com.evenjoin.diet_ms.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.evenjoin.diet_ms.entity.Subcategory;

import feign.Param;

public interface SubcategoryRepo extends JpaRepository<Subcategory, Long> {

	// Count subcategories
	@Query("SELECT COUNT(sc) " +
			"FROM Subcategory sc")
	public Integer countSubcategories();

	// Get subcategories by recipe book
	@Query("SELECT sc " +
			"FROM Subcategory sc " +
			"JOIN sc.category c " +
			"JOIN c.recipeBook rb " +
			"WHERE rb.idRecipeBook = :idRecipeBook")
	public List<Subcategory> getSubcategoriesByRecipeBook(@Param("idRecipeBook") Long idRecipeBook);

	// Get subcategories by category
	@Query("SELECT sc " +
			"FROM Subcategory sc " +
			"JOIN sc.category c " +
			"WHERE c.idCategory = :idCategory")
	public List<Subcategory> getSubcategoriesByCategory(@Param("idCategory") Long idCategory);

	// Get maximum comsumption subcategory
	@Query("SELECT sc " +
			"FROM Subcategory sc " +
			"JOIN CommonIngredient ci ON ci.subcategory.idSubcategory = sc.idSubcategory " +
			"JOIN Ingredient i ON i.commonIngredient.idCommonIngredient = ci.idCommonIngredient " +
			"JOIN RecipeIngredient ri ON ri.ingredient.idIngredient = i.idIngredient " +
			"JOIN Recipe r ON r.idRecipe = ri.recipe.idRecipe " +
			"JOIN DietRecipe dr ON dr.recipe.idRecipe = r.idRecipe " +
			"JOIN Diet d ON d.idDiet = dr.diet.idDiet " +
			"JOIN ScheduleDiet sd ON sd.diet.idDiet = d.idDiet " +
			"WHERE sd.isChecked = true " +
			"GROUP BY sc.idSubcategory " +
			"ORDER BY COUNT(sd.isChecked) DESC")
	public Subcategory getMaxConsumptionSubcategory();

	// Get minimum comsumption subcategory
	@Query("SELECT sc " +
			"FROM Subcategory sc " +
			"JOIN CommonIngredient ci ON ci.subcategory.idSubcategory = sc.idSubcategory " +
			"JOIN Ingredient i ON i.commonIngredient.idCommonIngredient = ci.idCommonIngredient " +
			"JOIN RecipeIngredient ri ON ri.ingredient.idIngredient = i.idIngredient " +
			"JOIN Recipe r ON r.idRecipe = ri.recipe.idRecipe " +
			"JOIN DietRecipe dr ON dr.recipe.idRecipe = r.idRecipe " +
			"JOIN Diet d ON d.idDiet = dr.diet.idDiet " +
			"JOIN ScheduleDiet sd ON sd.diet.idDiet = d.idDiet " +
			"WHERE sd.isChecked = true " +
			"GROUP BY sc.idSubcategory " +
			"ORDER BY COUNT(sd.isChecked) ASC")
	public Subcategory getMinConsumptionSubcategory();

}
