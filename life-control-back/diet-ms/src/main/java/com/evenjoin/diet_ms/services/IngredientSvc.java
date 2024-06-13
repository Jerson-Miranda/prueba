package com.evenjoin.diet_ms.services;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.evenjoin.diet_ms.entity.Ingredient;
import com.evenjoin.diet_ms.repository.IngredientRepo;
import com.evenjoin.diet_ms.services.interfaces.IIngredientSvc;

@Service
public class IngredientSvc implements IIngredientSvc {

	@Autowired
	private IngredientRepo ingredientRepo;
	
	@Override
	@Transactional(readOnly = true)
	public List<Ingredient> getIngredients() {
		return ingredientRepo.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Ingredient getIngredient(Long idIngredient) {
		return ingredientRepo.findById(idIngredient).orElse(null);
	}

	@Override
	@Transactional
	public Ingredient addIngredient(Ingredient ingredient) {
		return ingredientRepo.save(ingredient);
	}

	@Override
	@Transactional
	public void deleteIngredient(Long idIngredient) {
		ingredientRepo.deleteById(idIngredient);
	}
	
	@Override
	@Transactional(readOnly = true)
	public Integer countIngredients() {
		return ingredientRepo.countIngredients();
	}

	@Override
	@Transactional(readOnly = true)
	public List<Ingredient> getIngredientsByCategory(Long IdCategory) {
		return ingredientRepo.getIngredientsByCategory(IdCategory);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Ingredient> getIngredientsBySubcategory(Long IdSubcategory) {
		return ingredientRepo.getIngredientsBySubcategory(IdSubcategory);
	}

	@Override
	@Transactional(readOnly = true)
	public Ingredient getIngredientWithMaxNutrient(String nutrient) {
		return ingredientRepo.getIngredientWithMaxNutrient(nutrient);
	}
	
	@Override
	@Transactional(readOnly = true)
	public Ingredient getIngredientWithMinNutrient(String nutrient) {
		return ingredientRepo.getIngredientWithMinNutrient(nutrient);
	}

	@Override
	@Transactional(readOnly = true)
	public BigDecimal getQuantityToConsume(String barcode) {
		return ingredientRepo.getQuantityToConsume(barcode);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Ingredient> getIngredientsByRecipe(Long idRecipeBook) {
		return ingredientRepo.getIngredientsByRecipe(idRecipeBook);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Ingredient> getIngredientsByMinStock(Integer stock) {
		return ingredientRepo.getIngredientsByMinStock(stock);
	}
	
	
	
}
