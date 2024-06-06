package com.evenjoin.diet_ms.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.evenjoin.diet_ms.entity.RecipeBook;
import com.evenjoin.diet_ms.repository.RecipeBookRepo;
import com.evenjoin.diet_ms.services.interfaces.IRecipeBookSvc;

@Service
public class RecipeBookSvc implements IRecipeBookSvc {

	@Autowired
	private RecipeBookRepo recipeBookRepo;
	
	@Override
	public List<RecipeBook> getRecipeBooks() {
		return recipeBookRepo.findAll();
	}

	@Override
	public RecipeBook getRecipeBook(Long idRecipeBook) {
		return recipeBookRepo.findById(idRecipeBook).orElse(null);
	}

	@Override
	public RecipeBook addRecipeBook(RecipeBook recipeBook) {
		return recipeBookRepo.save(recipeBook);
	}

	@Override
	public void deleteRecipeBook(Long idRecipeBook) {
		recipeBookRepo.deleteById(idRecipeBook);
	}

}
