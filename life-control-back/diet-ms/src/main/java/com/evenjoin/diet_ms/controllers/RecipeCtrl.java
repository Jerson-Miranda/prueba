package com.evenjoin.diet_ms.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.evenjoin.diet_ms.entity.Recipe;
import com.evenjoin.diet_ms.services.RecipeSvc;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;

@RefreshScope
@RestController
@RequestMapping("/diet")
public class RecipeCtrl {

	private static final Logger logger = LoggerFactory.getLogger(RecipeCtrl.class);

	@Autowired
	private RecipeSvc recipeSvc;

	// Get all recipes
	@CircuitBreaker(name = "recipeBreaker", fallbackMethod = "getListObjectCB")
	@TimeLimiter(name = "recipeBreaker")
	@GetMapping("/recipe/all")
	@ResponseStatus(code = HttpStatus.OK)
	public CompletableFuture<List<Recipe>> getRecipes() {
		return CompletableFuture.supplyAsync(()-> recipeSvc.getRecipes());
	}

	// Get a recipe
	@CircuitBreaker(name = "recipeBreaker", fallbackMethod = "getObjectCB")
	@TimeLimiter(name = "recipeBreaker")
	@GetMapping("/recipe/{idRecipe}")
	@ResponseStatus(code = HttpStatus.OK)
	public CompletableFuture<Recipe> getRecipe(@PathVariable Long idRecipe) {
		return CompletableFuture.supplyAsync(()->recipeSvc.getRecipe(idRecipe));
	}

	// Add a recipe
	@CircuitBreaker(name = "recipeBreaker", fallbackMethod = "getObjectCB")
	@TimeLimiter(name = "recipeBreaker")
	@PostMapping("/recipe/add")
	@ResponseStatus(code = HttpStatus.CREATED)
	public CompletableFuture<Recipe> addRecipe(@RequestBody Recipe recipe) {
		return CompletableFuture.supplyAsync(()->recipeSvc.addRecipe(recipe));
	}

	// Update a recipe
	@CircuitBreaker(name = "recipeBreaker", fallbackMethod = "getObjectCB")
	@TimeLimiter(name = "recipeBreaker")
	@PutMapping("/recipe/update/{idRecipe}")
	@ResponseStatus(code = HttpStatus.OK)
	public CompletableFuture<Recipe> updateRecipe(@RequestBody Recipe recipe, @PathVariable Long idRecipe) {
		return CompletableFuture.supplyAsync(()-> {
			Recipe currentRecipe = recipeSvc.getRecipe(idRecipe);
			currentRecipe.setName(recipe.getName());
			currentRecipe.setProcedure_text(recipe.getProcedure_text());
			currentRecipe.setPhoto(recipe.getPhoto());
			currentRecipe.setTimeMinute(recipe.getTimeMinute());
			currentRecipe.setSubcategory(recipe.getSubcategory());
			currentRecipe.setRecipeBook(recipe.getRecipeBook());
			return recipeSvc.addRecipe(currentRecipe);
		});
	}

	// Delete a recipe
	@CircuitBreaker(name = "recipeBreaker", fallbackMethod = "getVoidCB")
	@TimeLimiter(name = "recipeBreaker")
	@DeleteMapping("/recipe/{idRecipe}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public CompletableFuture<Void> deleteRecipe(@PathVariable Long idRecipe) {
		return CompletableFuture.runAsync(()-> recipeSvc.deleteRecipe(idRecipe));
	}

	// (CircuitBreaker) Get void circuit breaker
	public CompletableFuture<Void> getVoidCB(Throwable t) {
		return CompletableFuture.runAsync(() -> logger.error("Enabled recipe breaker" + t));
	}

	// (CircuitBreaker) Get object circuit breaker
	public CompletableFuture<Recipe> getObjectCB(Throwable t) {
		logger.error("Enabled recipe breaker" + t);
		return CompletableFuture.supplyAsync(() -> {
			Recipe recipe = new Recipe();
			recipe.setIdRecipe(null);
			recipe.setName(null);
			recipe.setProcedure_text(null);
			recipe.setPhoto(null);
			recipe.setTimeMinute(0);
			recipe.setSubcategory(null);
			recipe.setRecipeBook(null);
			return recipe;
		});
	}

	// (CircuitBreaker) Get list object circuit breaker
	public CompletableFuture<List<Recipe>> getListObjectCB(Throwable t) {
		logger.error("Enabled recipe breaker" + t);
		return CompletableFuture.supplyAsync(() -> {
			List<Recipe> list = new ArrayList<Recipe>();
			Recipe recipe = new Recipe();
			recipe.setIdRecipe(null);
			recipe.setName(null);
			recipe.setProcedure_text(null);
			recipe.setPhoto(null);
			recipe.setTimeMinute(0);
			recipe.setSubcategory(null);
			recipe.setRecipeBook(null);
			list.add(recipe);
			return list;
		});
	}
}
