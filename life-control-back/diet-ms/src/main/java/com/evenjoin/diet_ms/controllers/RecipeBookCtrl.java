package com.evenjoin.diet_ms.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.evenjoin.diet_ms.entity.RecipeBook;
import com.evenjoin.diet_ms.services.RecipeBookSvc;

@RefreshScope
@RestController
@RequestMapping("/diet")
public class RecipeBookCtrl {

	@Autowired
	private RecipeBookSvc recipeBookSvc;

	//Get all recipe books
	@GetMapping("/recipe-book/all")
	@ResponseStatus(code = HttpStatus.OK)
	private List<RecipeBook> getRecipeBooks() {
		return recipeBookSvc.getRecipeBooks();
	}
	
	//Get a recipe book
	@GetMapping("/recipe-book/{idRecipeBook}")
	@ResponseStatus(code = HttpStatus.OK)
	private RecipeBook getRecipeBook(@PathVariable Long idRecipeBook) {
		return recipeBookSvc.getRecipeBook(idRecipeBook);
	}
	
	//Add a recipe book
	@PostMapping("/recipe-book/add")
	@ResponseStatus(code = HttpStatus.CREATED)
	private RecipeBook addRecipeBook(@RequestBody RecipeBook recipeBook) {
		return recipeBookSvc.addRecipeBook(recipeBook);
	}
	
	//Update a recipe book
	@PutMapping("/recipe-book/update/{idRecipeBook}")
	@ResponseStatus(code = HttpStatus.OK)
	private RecipeBook updateRecipeBook(@RequestBody RecipeBook recipeBook, @PathVariable Long idRecipeBook) {
		RecipeBook currentRecipeBook = recipeBookSvc.getRecipeBook(idRecipeBook);
		currentRecipeBook.setName(recipeBook.getName());
		currentRecipeBook.setProcedure_text(recipeBook.getProcedure_text());
		currentRecipeBook.setPhoto(recipeBook.getPhoto());
		currentRecipeBook.setTimeMinute(recipeBook.getTimeMinute());
		currentRecipeBook.setSubcategory(recipeBook.getSubcategory());
		return recipeBookSvc.addRecipeBook(currentRecipeBook);
	}
	
	//Delete a recipe book
	@DeleteMapping("/recipe-book/{idRecipeBook}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	private void deleteRecipeBook(@PathVariable Long idRecipeBook) {
		recipeBookSvc.deleteRecipeBook(idRecipeBook);
	}
}
