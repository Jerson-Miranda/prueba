package com.evenjoin.diet_ms.services.interfaces;

import java.util.List;

import com.evenjoin.diet_ms.entity.Recipe;

public interface IRecipeSvc {

	public List<Recipe> getRecipes();
	public Recipe getRecipe(Long idRecipe);
	public Recipe addRecipe(Recipe recipe);
	public void deleteRecipe(Long idRecipe);
	
}
