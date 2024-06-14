package com.evenjoin.diet_ms.services;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.evenjoin.diet_ms.entity.Recipe;
import com.evenjoin.diet_ms.repository.RecipeRepo;
import com.evenjoin.diet_ms.services.interfaces.IRecipeSvc;

@Service
public class RecipeSvc implements IRecipeSvc {

	@Autowired
	private RecipeRepo recipeRepo;
	
	@Override
	@Transactional(readOnly = true)
	public List<Recipe> getRecipes() {
		return recipeRepo.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Recipe getRecipe(Long idRecipe) {
		return recipeRepo.findById(idRecipe).orElse(null);
	}

	@Override
	@Transactional
	public Recipe addRecipe(Recipe recipe) {
		return recipeRepo.save(recipe);
	}

	@Override
	@Transactional
	public void deleteRecipe(Long idRecipe) {
		recipeRepo.deleteById(idRecipe);
	}

	@Override
	@Transactional(readOnly = true)
	public Integer countRecipes() {
		return recipeRepo.countRecipes();
	}

	@Override
	@Transactional(readOnly = true)
	public Integer countRecipesByRecipeBook(Long idRecipeBook) {
		return recipeRepo.countRecipesByRecipeBook(idRecipeBook);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Recipe> getRecipesByRecipeBook(Long idRecipeBook) {
		return recipeRepo.getRecipesByRecipeBook(idRecipeBook);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Recipe> getRecipesBySubcategory(Long idSubcategory) {
		return recipeRepo.getRecipesBySubcategory(idSubcategory);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Recipe> getRecipesByCategory(Long idCategory) {
		return recipeRepo.getRecipesByCategory(idCategory);
	}

	@Override
	public List<Recipe> getRecipesByIngredient(Long idIngredient) {
		return recipeRepo.getRecipesByIngredient(idIngredient);
	}

	@Override
	public Object getMacronutrientsByRecipe(Long idRecipe) {
		return recipeRepo.getMacronutrientsByRecipe(idRecipe); 
	}

	@Override
	public Recipe getRecipeWithMaxMacronutrient(String macronutrient) {
		return recipeRepo.getRecipeWithMaxMacronutrient(macronutrient);
	}

	@Override
	public Recipe getRecipeWithMinMacronutrient(String macronutrient) {
		return recipeRepo.getRecipeWithMinMacronutrient(macronutrient);
	}

	@Override
	public BigDecimal getPriceByRecipe(Long idRecipe) {
		return recipeRepo.getPriceByRecipe(idRecipe);
	}

}
