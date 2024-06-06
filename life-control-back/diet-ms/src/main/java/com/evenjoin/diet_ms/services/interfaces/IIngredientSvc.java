package com.evenjoin.diet_ms.services.interfaces;

import java.math.BigDecimal;
import java.util.List;
import com.evenjoin.diet_ms.entity.Ingredient;

public interface IIngredientSvc {

	public List<Ingredient> getIngredients();
	public Ingredient getIngredient(Long idIngredient);
	public Ingredient addIngredient(Ingredient ingredient);
	public void deleteIngredient(Long idIngredient);
	public List<Ingredient> getIngredientsByCategory(Long IdCategory);
	public List<Ingredient> getIngredientsBySubcategory(Long IdSubcategory);
	public Ingredient getIngredientWithMaxNutrient(String nutrient);
	public Ingredient getIngredientWithMinNutrient(String nutrient);
	public BigDecimal getQuantityToConsume(Long idIngredient);
	
}
