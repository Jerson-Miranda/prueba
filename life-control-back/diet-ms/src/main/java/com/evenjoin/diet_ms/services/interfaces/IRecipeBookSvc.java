package com.evenjoin.diet_ms.services.interfaces;

import java.util.List;
import com.evenjoin.diet_ms.entity.RecipeBook;

public interface IRecipeBookSvc {

    public List<RecipeBook> getRecipeBooks();
	public RecipeBook getRecipeBook(Long idRecipeBook);
	public RecipeBook addRecipeBook(RecipeBook recipeBook);
	public void deleteRecipeBook(Long idRecipeBook);
	public Integer countRecipeBooks();
    public RecipeBook getRecipeBookByRecipe(Long idRecipe);
    public List<RecipeBook> getFavoriteRecipeBooks();
	public RecipeBook getMaxConsumptionRecipeBook();
	public RecipeBook getMinConsumptionRecipeBook();
}