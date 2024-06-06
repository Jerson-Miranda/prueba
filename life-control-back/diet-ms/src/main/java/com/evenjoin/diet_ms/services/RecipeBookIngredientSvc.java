package com.evenjoin.diet_ms.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.evenjoin.diet_ms.repository.RecipeBookIngredientRepo;
import com.evenjoin.diet_ms.services.interfaces.IRecipeBookIngredientSvc;

@Service
public class RecipeBookIngredientSvc implements IRecipeBookIngredientSvc {

	@Autowired
	private RecipeBookIngredientRepo recipeBookIngredientRepo;
	
}
