package com.evenjoin.diet_ms.services.interfaces;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import com.evenjoin.diet_ms.entity.Ingredient;

public interface IIngredientSvc {

	public List<Ingredient> getIngredients();
	public Ingredient getIngredient(Long idIngredient);
	public Ingredient addIngredient(Ingredient ingredient);
	public void deleteIngredient(Long idIngredient);
	public Integer countIngredients();
	public List<Ingredient> getIngredientsByCategory(Long IdCategory);
	public List<Ingredient> getIngredientsBySubcategory(Long IdSubcategory);
	public Ingredient getIngredientWithMaxNutrient(String nutrient);
	public Ingredient getIngredientWithMinNutrient(String nutrient);
	public BigDecimal getQuantityToConsume(String barcode);
	public List<Ingredient> getIngredientsByRecipe(Long idRecipeBook);
	public List<Ingredient> getIngredientsByMinStock(Integer stock);
	public List<Ingredient> getFavoriteIngredients();
	public List<Object> getIngredientsByDiet(Long idDiet);
	public List<Object> getIngredientsByDietRange(Date startDate, Date endDate);
	public Ingredient getMaxConsumptionIngredient();
	public Ingredient getMinConsumptionIngredient();

}
