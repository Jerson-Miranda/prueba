package com.evenjoin.diet_ms.services;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.evenjoin.diet_ms.entity.Ingredient;
import com.evenjoin.diet_ms.repository.IngredientRepo;
import com.evenjoin.diet_ms.services.interfaces.IIngredientSvc;

@Service
public class IngredientSvc implements IIngredientSvc {

	@Autowired
	private IngredientRepo ingredientRepo;
	
	@Override
	public List<Ingredient> getIngredients() {
		return ingredientRepo.findAll();
	}

	@Override
	public Ingredient getIngredient(Long idIngredient) {
		return ingredientRepo.findById(idIngredient).orElse(null);
	}

	@Override
	public Ingredient addIngredient(Ingredient ingredient) {
		return ingredientRepo.save(ingredient);
	}

	@Override
	public void deleteIngredient(Long idIngredient) {
		ingredientRepo.deleteById(idIngredient);
	}

	@Override
	public List<Ingredient> getIngredientsByCategory(Long IdCategory) {
		return ingredientRepo.getIngredientsByCategory(IdCategory);
	}

	@Override
	public List<Ingredient> getIngredientsBySubcategory(Long IdSubcategory) {
		return ingredientRepo.getIngredientsBySubcategory(IdSubcategory);
	}

	@Override
	public Ingredient getIngredientWithMaxNutrient(String nutrient) {
		return ingredientRepo.getIngredientWithMaxNutrient(nutrient);
	}
	
	@Override
	public Ingredient getIngredientWithMinNutrient(String nutrient) {
		return ingredientRepo.getIngredientWithMinNutrient(nutrient);
	}

	@Override
	public BigDecimal getQuantityToConsume(Long idIngredient) {
		return ingredientRepo.getQuantityToConsume(idIngredient);
	}

}
