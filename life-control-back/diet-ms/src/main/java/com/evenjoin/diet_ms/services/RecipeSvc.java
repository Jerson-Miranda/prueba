package com.evenjoin.diet_ms.services;

import java.math.BigDecimal;
import java.util.Date;
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
	@Transactional(readOnly = true)
	public List<Recipe> getRecipesByIngredient(Long idIngredient) {
		return recipeRepo.getRecipesByIngredient(idIngredient);
	}

	@Override
	@Transactional(readOnly = true)
	public Object getMacronutrientsByRecipe(Long idRecipe) {
		return recipeRepo.getMacronutrientsByRecipe(idRecipe); 
	}

	@Override
	@Transactional(readOnly = true)
	public Recipe getRecipeWithMaxMacronutrient(String macronutrient) {
		return recipeRepo.getRecipeWithMaxMacronutrient(macronutrient);
	}

	@Override
	@Transactional(readOnly = true)
	public Recipe getRecipeWithMinMacronutrient(String macronutrient) {
		return recipeRepo.getRecipeWithMinMacronutrient(macronutrient);
	}

	@Override
	@Transactional(readOnly = true)
	public Object getVitaminsByRecipe(Long idRecipe) {
		return recipeRepo.getVitaminsByRecipe(idRecipe);
	}

	@Override
	@Transactional(readOnly = true)
	public Recipe getRecipeWithMaxVitamin(String vitamin) {
		return recipeRepo.getRecipeWithMaxVitamin(vitamin);
	}

	@Override
	@Transactional(readOnly = true)
	public Recipe getRecipeWithMinVitamin(String vitamin) {
		return recipeRepo.getRecipeWithMinVitamin(vitamin);
	}

	@Override
	@Transactional(readOnly = true)
	public Object getMineralsByRecipe(Long idRecipe) {
		return recipeRepo.getMineralsByRecipe(idRecipe);
	}

	@Override
	@Transactional(readOnly = true)
	public Recipe getRecipeWithMaxMineral(String mineral) {
		return recipeRepo.getRecipeWithMaxMineral(mineral);
	}

	@Override
	@Transactional(readOnly = true)
	public Recipe getRecipeWithMinMineral(String mineral) {
		return recipeRepo.getRecipeWithMinMineral(mineral);
	}

	@Override
	@Transactional(readOnly = true)
	public BigDecimal getPriceByRecipe(Long idRecipe) {
		return recipeRepo.getPriceByRecipe(idRecipe);
	}

	@Override
	@Transactional(readOnly = true)
	public Recipe getRecipeWithMaxPrice() {
		return recipeRepo.getRecipeWithMaxPrice();
	}

	@Override
	@Transactional(readOnly = true)
	public Recipe getRecipeWithMinPrice() {
		return recipeRepo.getRecipeWithMinPrice();
	}

	@Override
	@Transactional(readOnly = true)
	public Recipe getRecipeWithMaxTime() {
		return recipeRepo.getRecipeWithMaxTime();
	}

	@Override
	@Transactional(readOnly = true)
	public Recipe getRecipeWithMinTime() {
		return recipeRepo.getRecipeWithMinTime();
	}

	@Override
	@Transactional(readOnly = true)
	public List<Recipe> getFavoriteRecipes() {
		return recipeRepo.getFavoriteRecipes();
	}

	@Override
	@Transactional(readOnly = true)
	public List<Object> getRecipesByDiet(Long idDiet) {
		return recipeRepo.getRecipesByDiet(idDiet);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Object> getRecipesByDietRange(Date startDate, Date endDate) {
		return recipeRepo.getRecipesByDietRange(startDate, endDate);
	}

}
