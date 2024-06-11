package com.evenjoin.diet_ms.services;

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

}
