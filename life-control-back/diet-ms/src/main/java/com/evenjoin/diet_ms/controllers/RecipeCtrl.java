package com.evenjoin.diet_ms.controllers;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
		return CompletableFuture.supplyAsync(() -> recipeSvc.getRecipes());
	}

	// Get a recipe
	@CircuitBreaker(name = "recipeBreaker", fallbackMethod = "getObjectCB")
	@TimeLimiter(name = "recipeBreaker")
	@GetMapping("/recipe/{idRecipe}")
	@ResponseStatus(code = HttpStatus.OK)
	public CompletableFuture<Recipe> getRecipe(@PathVariable Long idRecipe) {
		return CompletableFuture.supplyAsync(() -> recipeSvc.getRecipe(idRecipe));
	}

	// Add a recipe
	@CircuitBreaker(name = "recipeBreaker", fallbackMethod = "getObjectCB")
	@TimeLimiter(name = "recipeBreaker")
	@PostMapping("/recipe/add")
	@ResponseStatus(code = HttpStatus.CREATED)
	public CompletableFuture<Recipe> addRecipe(@RequestBody Recipe recipe) {
		return CompletableFuture.supplyAsync(() -> recipeSvc.addRecipe(recipe));
	}

	// Update a recipe
	@CircuitBreaker(name = "recipeBreaker", fallbackMethod = "getObjectCB")
	@TimeLimiter(name = "recipeBreaker")
	@PutMapping("/recipe/update/{idRecipe}")
	@ResponseStatus(code = HttpStatus.OK)
	public CompletableFuture<Recipe> updateRecipe(@RequestBody Recipe recipe, @PathVariable Long idRecipe) {
		return CompletableFuture.supplyAsync(() -> {
			Recipe currentRecipe = recipeSvc.getRecipe(idRecipe);
			currentRecipe.setName(recipe.getName());
			currentRecipe.setProcedure_text(recipe.getProcedure_text());
			currentRecipe.setPhoto(recipe.getPhoto());
			currentRecipe.setTimeMinute(recipe.getTimeMinute());
			currentRecipe.setIsFavorite(recipe.getIsFavorite());
			currentRecipe.setSubcategory(recipe.getSubcategory());
			return recipeSvc.addRecipe(currentRecipe);
		});
	}

	// Delete a recipe
	@CircuitBreaker(name = "recipeBreaker", fallbackMethod = "getVoidCB")
	@TimeLimiter(name = "recipeBreaker")
	@DeleteMapping("/recipe/{idRecipe}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public CompletableFuture<Void> deleteRecipe(@PathVariable Long idRecipe) {
		return CompletableFuture.runAsync(() -> recipeSvc.deleteRecipe(idRecipe));
	}

	// Count recipes
	@CircuitBreaker(name = "recipeBreaker", fallbackMethod = "getMapIntegerCB")
	@TimeLimiter(name = "recipeBreaker")
	@GetMapping("/recipe/count")
	@ResponseStatus(code = HttpStatus.OK)
	public CompletableFuture<Map<String, Integer>> countRecipes() {
		return CompletableFuture.supplyAsync(() -> {
			Map<String, Integer> jsonObject = new HashMap<String, Integer>();
			jsonObject.put("count", recipeSvc.countRecipes());
			return jsonObject;
		});
	}

	// Count recipes by recipe book
	@CircuitBreaker(name = "recipeBreaker", fallbackMethod = "getMapIntegerCB")
	@TimeLimiter(name = "recipeBreaker")
	@GetMapping("/recipe/count/{idRecipeBook}")
	@ResponseStatus(code = HttpStatus.OK)
	public CompletableFuture<Map<String, Integer>> countRecipesByRecipeBook(@PathVariable Long idRecipeBook) {
		return CompletableFuture.supplyAsync(() -> {
			Map<String, Integer> jsonObject = new HashMap<String, Integer>();
			jsonObject.put("count", recipeSvc.countRecipesByRecipeBook(idRecipeBook));
			return jsonObject;
		});
	}

	// Get recipes by recipe book
	@CircuitBreaker(name = "recipeBreaker", fallbackMethod = "getListObjectCB")
	@TimeLimiter(name = "recipeBreaker")
	@GetMapping("/recipe/recipe-book/{idRecipeBook}")
	@ResponseStatus(code = HttpStatus.OK)
	public CompletableFuture<List<Recipe>> getRecipesByRecipeBook(@PathVariable Long idRecipeBook) {
		return CompletableFuture.supplyAsync(() -> recipeSvc.getRecipesByRecipeBook(idRecipeBook));
	}

	// Get recipes by subcategory
	@CircuitBreaker(name = "recipeBreaker", fallbackMethod = "getListObjectCB")
	@TimeLimiter(name = "recipeBreaker")
	@GetMapping("/recipe/subcategory/{idSubcategory}")
	@ResponseStatus(code = HttpStatus.OK)
	public CompletableFuture<List<Recipe>> getRecipesBySubcategory(@PathVariable Long idSubcategory) {
		return CompletableFuture.supplyAsync(() -> recipeSvc.getRecipesBySubcategory(idSubcategory));
	}

	// Get recipes by category
	@CircuitBreaker(name = "recipeBreaker", fallbackMethod = "getListObjectCB")
	@TimeLimiter(name = "recipeBreaker")
	@GetMapping("/recipe/category/{idCategory}")
	@ResponseStatus(code = HttpStatus.OK)
	public CompletableFuture<List<Recipe>> getRecipesByCategory(@PathVariable Long idCategory) {
		return CompletableFuture.supplyAsync(() -> recipeSvc.getRecipesByCategory(idCategory));
	}

	// Get recipes by ingredient
	@CircuitBreaker(name = "recipeBreaker", fallbackMethod = "getListObjectCB")
	@TimeLimiter(name = "recipeBreaker")
	@GetMapping("/recipe/ingredient/{idIngredient}")
	@ResponseStatus(code = HttpStatus.OK)
	public CompletableFuture<List<Recipe>> getRecipesByIngredient(@PathVariable Long idIngredient) {
		return CompletableFuture.supplyAsync(() -> recipeSvc.getRecipesByIngredient(idIngredient));
	}

	// Get macronutrients by recipe
	@CircuitBreaker(name = "recipeBreaker", fallbackMethod = "getMapMacronutrientCB")
	@TimeLimiter(name = "recipeBreaker")
	@GetMapping("/recipe/macronutrient/{idRecipe}")
	@ResponseStatus(code = HttpStatus.OK)
	public CompletableFuture<Map<String, Object>> getMacronutrientsByRecipe(@PathVariable Long idRecipe) {
		return CompletableFuture.supplyAsync(() -> {
			Object response = recipeSvc.getMacronutrientsByRecipe(idRecipe);
			Object[] res = (Object[]) response;
			Map<String, Object> jsonObject = new HashMap<String, Object>();
			jsonObject.put("kcal", res[0]);
			jsonObject.put("protein", res[1]);
			jsonObject.put("carbohydrate", res[2]);
			jsonObject.put("sugar", res[3]);
			jsonObject.put("addedSugar", res[4]);
			jsonObject.put("fat", res[5]);
			jsonObject.put("saturatedFat", res[6]);
			jsonObject.put("transFat", res[7]);
			jsonObject.put("fiber", res[8]);
			jsonObject.put("sodium", res[9]);
			return jsonObject;
		});
	}

	// Get recipe with maximum macronutrient
	@CircuitBreaker(name = "recipeBreaker", fallbackMethod = "getObjectCB")
	@TimeLimiter(name = "recipeBreaker")
	@GetMapping("/recipe/max-macronutrient/{macronutrient}")
	@ResponseStatus(code = HttpStatus.OK)
	public CompletableFuture<Recipe> getRecipeWithMaxMacronutrient(@PathVariable String macronutrient) {
		return CompletableFuture.supplyAsync(() -> recipeSvc.getRecipeWithMaxMacronutrient(macronutrient));
	}

	// Get recipe with minumum macronutrient
	@CircuitBreaker(name = "recipeBreaker", fallbackMethod = "getObjectCB")
	@TimeLimiter(name = "recipeBreaker")
	@GetMapping("/recipe/min-macronutrient/{macronutrient}")
	@ResponseStatus(code = HttpStatus.OK)
	public CompletableFuture<Recipe> getRecipeWithMinMacronutrient(@PathVariable String macronutrient) {
		return CompletableFuture.supplyAsync(() -> recipeSvc.getRecipeWithMinMacronutrient(macronutrient));
	}

	// Get vitamins by recipe
	@CircuitBreaker(name = "recipeBreaker", fallbackMethod = "getMapVitaminCB")
	@TimeLimiter(name = "recipeBreaker")
	@GetMapping("/recipe/vitamin/{idRecipe}")
	@ResponseStatus(code = HttpStatus.OK)
	public CompletableFuture<Map<String, Object>> getVitaminsByRecipe(@PathVariable Long idRecipe) {
		return CompletableFuture.supplyAsync(() -> {
			Object response = recipeSvc.getVitaminsByRecipe(idRecipe);
			Object[] res = (Object[]) response;
			Map<String, Object> jsonObject = new HashMap<String, Object>();
			jsonObject.put("vitaminA", res[0]);
			jsonObject.put("vitaminB", res[1]);
			jsonObject.put("vitaminC", res[2]);
			jsonObject.put("vitaminD", res[3]);
			jsonObject.put("vitaminE", res[4]);
			jsonObject.put("vitaminK", res[5]);
			return jsonObject;
		});
	}

	// Get recipe with maximum vitamin
	@CircuitBreaker(name = "recipeBreaker", fallbackMethod = "getObjectCB")
	@TimeLimiter(name = "recipeBreaker")
	@GetMapping("/recipe/max-vitamin/{vitamin}")
	@ResponseStatus(code = HttpStatus.OK)
	public CompletableFuture<Recipe> getRecipeWithMaxVitamin(@PathVariable String vitamin) {
		return CompletableFuture.supplyAsync(() -> recipeSvc.getRecipeWithMaxVitamin(vitamin));
	}

	// Get recipe with minimum vitamin
	@CircuitBreaker(name = "recipeBreaker", fallbackMethod = "getObjectCB")
	@TimeLimiter(name = "recipeBreaker")
	@GetMapping("/recipe/min-vitamin/{vitamin}")
	@ResponseStatus(code = HttpStatus.OK)
	public CompletableFuture<Recipe> getRecipeWithMinVitamin(@PathVariable String vitamin) {
		return CompletableFuture.supplyAsync(() -> recipeSvc.getRecipeWithMinVitamin(vitamin));
	}

	// Get minerals by recipe
	@CircuitBreaker(name = "recipeBreaker", fallbackMethod = "getMapMineralCB")
	@TimeLimiter(name = "recipeBreaker")
	@GetMapping("/recipe/mineral/{idRecipe}")
	@ResponseStatus(code = HttpStatus.OK)
	public CompletableFuture<Map<String, Object>> getMineralsByRecipe(@PathVariable Long idRecipe) {
		return CompletableFuture.supplyAsync(() -> {
			Object response = recipeSvc.getMineralsByRecipe(idRecipe);
			Object[] res = (Object[]) response;
			Map<String, Object> jsonObject = new HashMap<String, Object>();
			jsonObject.put("calcium", res[0]);
			jsonObject.put("phosphorus", res[1]);
			jsonObject.put("potassium", res[2]);
			jsonObject.put("sodium", res[3]);
			jsonObject.put("chloride", res[4]);
			jsonObject.put("magnesium", res[5]);
			jsonObject.put("sulfur", res[6]);
			jsonObject.put("iron", res[7]);
			jsonObject.put("zinc", res[8]);
			jsonObject.put("copper", res[9]);
			jsonObject.put("manganese", res[10]);
			jsonObject.put("iodine", res[11]);
			jsonObject.put("selenium", res[12]);
			jsonObject.put("molybdenum", res[13]);
			jsonObject.put("cobalt", res[14]);
			jsonObject.put("fluoride", res[15]);
			jsonObject.put("chromium", res[16]);
			return jsonObject;
		});
	}

	// Get recipe with maximum mineral
	@CircuitBreaker(name = "recipeBreaker", fallbackMethod = "getObjectCB")
	@TimeLimiter(name = "recipeBreaker")
	@GetMapping("/recipe/max-mineral/{mineral}")
	@ResponseStatus(code = HttpStatus.OK)
	public CompletableFuture<Recipe> getRecipeWithMaxMineral(@PathVariable String mineral) {
		return CompletableFuture.supplyAsync(() -> recipeSvc.getRecipeWithMaxMineral(mineral));
	}

	// Get recipe with minimum mineral
	@CircuitBreaker(name = "recipeBreaker", fallbackMethod = "getObjectCB")
	@TimeLimiter(name = "recipeBreaker")
	@GetMapping("/recipe/min-mineral/{mineral}")
	@ResponseStatus(code = HttpStatus.OK)
	public CompletableFuture<Recipe> getRecipeWithMinMineral(@PathVariable String mineral) {
		return CompletableFuture.supplyAsync(() -> recipeSvc.getRecipeWithMinMineral(mineral));
	}

	// Get price by recipe
	@CircuitBreaker(name = "recipeBreaker", fallbackMethod = "getMapDecimalCB")
	@TimeLimiter(name = "recipeBreaker")
	@GetMapping("/recipe/price/{idRecipe}")
	@ResponseStatus(code = HttpStatus.OK)
	public CompletableFuture<Map<String, BigDecimal>> getPriceByRecipe(@PathVariable Long idRecipe) {
		return CompletableFuture.supplyAsync(() -> {
			Map<String, BigDecimal> jsonObject = new HashMap<String, BigDecimal>();
			jsonObject.put("price", recipeSvc.getPriceByRecipe(idRecipe));
			return jsonObject;
		});
	}

	// Get recipe with maximum price
	@CircuitBreaker(name = "recipeBreaker", fallbackMethod = "getObjectCB")
	@TimeLimiter(name = "recipeBreaker")
	@GetMapping("/recipe/max-price")
	@ResponseStatus(code = HttpStatus.OK)
	public CompletableFuture<Recipe> getRecipeWithMaxPrice() {
		return CompletableFuture.supplyAsync(() -> recipeSvc.getRecipeWithMaxPrice());
	}

	// Get recipe with minimum price
	@CircuitBreaker(name = "recipeBreaker", fallbackMethod = "getObjectCB")
	@TimeLimiter(name = "recipeBreaker")
	@GetMapping("/recipe/min-price")
	@ResponseStatus(code = HttpStatus.OK)
	public CompletableFuture<Recipe> getRecipeWithMinPrice() {
		return CompletableFuture.supplyAsync(() -> recipeSvc.getRecipeWithMinPrice());
	}

	// Get recipe with maximum time
	@CircuitBreaker(name = "recipeBreaker", fallbackMethod = "getObjectCB")
	@TimeLimiter(name = "recipeBreaker")
	@GetMapping("/recipe/max-time")
	@ResponseStatus(code = HttpStatus.OK)
	public CompletableFuture<Recipe> getRecipeWithMaxTime() {
		return CompletableFuture.supplyAsync(() -> recipeSvc.getRecipeWithMaxTime());
	}

	// Get recipe with minimum time
	@CircuitBreaker(name = "recipeBreaker", fallbackMethod = "getObjectCB")
	@TimeLimiter(name = "recipeBreaker")
	@GetMapping("/recipe/min-time")
	@ResponseStatus(code = HttpStatus.OK)
	public CompletableFuture<Recipe> getRecipeWithMinTime() {
		return CompletableFuture.supplyAsync(() -> recipeSvc.getRecipeWithMinTime());
	}

	// Get favorite recipes
	@CircuitBreaker(name = "recipeBreaker", fallbackMethod = "getListObjectCB")
	@TimeLimiter(name = "recipeBreaker")
	@GetMapping("/recipe/favorite")
	@ResponseStatus(code = HttpStatus.OK)
	public CompletableFuture<List<Recipe>> getFavoriteRecipes() {
		return CompletableFuture.supplyAsync(() -> recipeSvc.getFavoriteRecipes());
	}

	// Get recipes by diet
	@CircuitBreaker(name = "recipeBreaker", fallbackMethod = "getListMapRecipeCB")
	@TimeLimiter(name = "recipeBreaker")
	@GetMapping("/recipe/diet/{idDiet}")
	@ResponseStatus(code = HttpStatus.OK)
	public CompletableFuture<List<Map<String, Object>>> getRecipesByDiet(@PathVariable Long idDiet) {
		return CompletableFuture.supplyAsync(() -> {
			List<Object> response = recipeSvc.getRecipesByDiet(idDiet);
			List<Map<String, Object>> json = new ArrayList<Map<String, Object>>();
			for (Object res : response) {
				Object[] row = (Object[]) res;
				Map<String, Object> jsonObject = new HashMap<String, Object>();
				jsonObject.put("idRecipe", row[0]);
				jsonObject.put("portion", row[1]);
				json.add(jsonObject);
			}
			return json;
		});
	}

	// Get recipes by diet between dates
	@CircuitBreaker(name = "recipeBreaker", fallbackMethod = "getListMapRecipeCB")
	@TimeLimiter(name = "recipeBreaker")
	@GetMapping("/recipe/diet/{startDate}/{endDate}")
	@ResponseStatus(code = HttpStatus.OK)
	public CompletableFuture<List<Map<String, Object>>> getRecipesByDietRange(@PathVariable String startDate,
			@PathVariable String endDate) {
		return CompletableFuture.supplyAsync(() -> {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			Date startDate1;
			Date endDate1;
			try {
				startDate1 = formatter.parse(startDate);
				endDate1 = formatter.parse(endDate);
			} catch (ParseException e) {
				throw new RuntimeException("Invalid date: " + e.getMessage());
			}
			List<Object> response = recipeSvc.getRecipesByDietRange(startDate1, endDate1);
			List<Map<String, Object>> json = new ArrayList<Map<String, Object>>();
			for (Object res : response) {
				Object[] row = (Object[]) res;
				Map<String, Object> jsonObject = new HashMap<String, Object>();
				jsonObject.put("idRecipe", row[0]);
				jsonObject.put("portion", row[1]);
				json.add(jsonObject);
			}
			return json;
		});
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
			recipe.setTimeMinute(null);
			recipe.setIsFavorite(null);
			recipe.setSubcategory(null);
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
			recipe.setTimeMinute(null);
			recipe.setIsFavorite(null);
			recipe.setSubcategory(null);
			list.add(recipe);
			return list;
		});
	}

	// (CircuitBreaker) Get map integer circuit breaker
	public CompletableFuture<Map<String, Integer>> getMapIntegerCB(Throwable t) {
		logger.error("Enabled recipe breaker " + t);
		return CompletableFuture.supplyAsync(() -> {
			Map<String, Integer> jsonObject = new HashMap<String, Integer>();
			jsonObject.put("count", null);
			return jsonObject;
		});
	}

	// (CircuitBreaker) Get map object circuit breaker
	public CompletableFuture<Map<String, Object>> getMapMacronutrientCB(Throwable t) {
		logger.error("Enabled recipe breaker" + t);
		return CompletableFuture.supplyAsync(() -> {
			Map<String, Object> jsonObject = new HashMap<>();
			jsonObject.put("kcal", null);
			jsonObject.put("protein", null);
			jsonObject.put("carbohydrate", null);
			jsonObject.put("sugar", null);
			jsonObject.put("addedSugar", null);
			jsonObject.put("fat", null);
			jsonObject.put("saturatedFat", null);
			jsonObject.put("transFat", null);
			jsonObject.put("fiber", null);
			jsonObject.put("sodium", null);
			return jsonObject;
		});
	}

	// (CircuitBreaker) Get map object circuit breaker
	public CompletableFuture<Map<String, Object>> getMapVitaminCB(Throwable t) {
		logger.error("Enabled recipe breaker" + t);
		return CompletableFuture.supplyAsync(() -> {
			Map<String, Object> jsonObject = new HashMap<>();
			jsonObject.put("vitaminA", null);
			jsonObject.put("vitaminB", null);
			jsonObject.put("vitaminC", null);
			jsonObject.put("vitaminD", null);
			jsonObject.put("vitaminE", null);
			jsonObject.put("vitaminK", null);
			return jsonObject;
		});
	}

	// (CircuitBreaker) Get map object circuit breaker
	public CompletableFuture<Map<String, Object>> getMapMineralCB(Throwable t) {
		logger.error("Enabled recipe breaker" + t);
		return CompletableFuture.supplyAsync(() -> {
			Map<String, Object> jsonObject = new HashMap<>();
			jsonObject.put("calcium", null);
			jsonObject.put("phosphorus", null);
			jsonObject.put("potassium", null);
			jsonObject.put("sodium", null);
			jsonObject.put("chloride", null);
			jsonObject.put("magnesium", null);
			jsonObject.put("sulfur", null);
			jsonObject.put("iron", null);
			jsonObject.put("zinc", null);
			jsonObject.put("copper", null);
			jsonObject.put("manganese", null);
			jsonObject.put("iodine", null);
			jsonObject.put("selenium", null);
			jsonObject.put("molybdenum", null);
			jsonObject.put("cobalt", null);
			jsonObject.put("fluoride", null);
			jsonObject.put("chromium", null);
			return jsonObject;
		});
	}

	// (CircuitBreaker) Get map decimal circuit breaker
	public CompletableFuture<Map<String, BigDecimal>> getMapDecimalCB(Throwable t) {
		logger.error("Enabled recipe breaker" + t);
		return CompletableFuture.supplyAsync(() -> {
			Map<String, BigDecimal> jsonObject = new HashMap<String, BigDecimal>();
			jsonObject.put("price", null);
			return jsonObject;
		});
	}

	// (CircuitBreaker) Get list map recipe circuit breaker
	public CompletableFuture<List<Map<String, Object>>> getListMapRecipeCB(Throwable t) {
		logger.error("Enabled recipe breaker" + t);
		return CompletableFuture.supplyAsync(() -> {
			List<Map<String, Object>> json = new ArrayList<Map<String, Object>>();
			Map<String, Object> jsonObject = new HashMap<String, Object>();
			jsonObject.put("idRecipe", null);
			jsonObject.put("portion", null);
			json.add(jsonObject);
			return json;
		});
	}

}
