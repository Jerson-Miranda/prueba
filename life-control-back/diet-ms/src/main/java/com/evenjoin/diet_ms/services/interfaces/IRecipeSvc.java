package com.evenjoin.diet_ms.services.interfaces;

import java.math.BigDecimal;
import java.util.List;
import com.evenjoin.diet_ms.entity.Recipe;

public interface IRecipeSvc {

	public List<Recipe> getRecipes();
	public Recipe getRecipe(Long idRecipe);
	public Recipe addRecipe(Recipe recipe);
	public void deleteRecipe(Long idRecipe);
	public Integer countRecipes();
	public Integer countRecipesByRecipeBook(Long idRecipeBook);
	public List<Recipe> getRecipesByRecipeBook(Long idRecipeBook);
	public List<Recipe> getRecipesBySubcategory(Long idSubcategory);
	public List<Recipe> getRecipesByCategory(Long idCategory);
	public List<Recipe> getRecipesByIngredient(Long idIngredient);
	public Object getMacronutrientsByRecipe(Long idRecipe);
	public Recipe getRecipeWithMaxMacronutrient(String macronutrient);
	public Recipe getRecipeWithMinMacronutrient(String macronutrient);
	public Object getVitaminsByRecipe(Long idRecipe);
	public Recipe getRecipeWithMaxVitamin(String vitamin);
	public Recipe getRecipeWithMinVitamin(String vitamin);
	public Object getMineralsByRecipe(Long idRecipe);
	public Recipe getRecipeWithMaxMineral(String mineral);
	public Recipe getRecipeWithMinMineral(String mineral);
	public BigDecimal getPriceByRecipe(Long idRecipe);
	public Recipe getRecipeWithMaxPrice();
	public Recipe getRecipeWithMinPrice();
	public Recipe getRecipeWithMaxTime();
	public Recipe getRecipeWithMinTime();
	public List<Recipe> getFavoriteRecipes();

}
