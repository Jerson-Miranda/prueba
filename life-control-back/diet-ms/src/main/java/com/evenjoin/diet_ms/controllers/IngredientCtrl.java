package com.evenjoin.diet_ms.controllers;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import com.evenjoin.diet_ms.entity.Ingredient;
import com.evenjoin.diet_ms.services.IngredientSvc;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;

@RefreshScope
@RestController
@RequestMapping("/diet")
public class IngredientCtrl {

	private static final Logger logger = LoggerFactory.getLogger(IngredientCtrl.class);

	@Autowired
	private IngredientSvc ingredientSvc;

	// Get all ingredients
	@CircuitBreaker(name = "ingredientBreaker", fallbackMethod = "getListObjectCB")
	@TimeLimiter(name = "ingredientBreaker")
	@GetMapping("/ingredient/all")
	@ResponseStatus(code = HttpStatus.OK)
	public CompletableFuture<List<Ingredient>> getIngredients() {
		return CompletableFuture.supplyAsync(() -> ingredientSvc.getIngredients());
	}

	// Get a ingredient
	@CircuitBreaker(name = "ingredientBreaker", fallbackMethod = "getObjectCB")
	@TimeLimiter(name = "ingredientBreaker")
	@GetMapping("/ingredient/{idIngredient}")
	@ResponseStatus(code = HttpStatus.OK)
	public CompletableFuture<Ingredient> getIngredient(@PathVariable Long idIngredient) {
		return CompletableFuture.supplyAsync(() -> ingredientSvc.getIngredient(idIngredient));
	}

	// Add a ingredient
	@CircuitBreaker(name = "ingredientBreaker", fallbackMethod = "getObjectCB")
	@TimeLimiter(name = "ingredientBreaker")
	@PostMapping("/ingredient/add")
	@ResponseStatus(code = HttpStatus.CREATED)
	public CompletableFuture<Ingredient> addIngredient(@RequestBody Ingredient ingredient) {
		return CompletableFuture.supplyAsync(() -> ingredientSvc.addIngredient(ingredient));
	}

	// Update a ingredient
	@CircuitBreaker(name = "ingredientBreaker", fallbackMethod = "getObjectCB")
	@TimeLimiter(name = "ingredientBreaker")
	@PutMapping("/ingredient/update/{idIngredient}")
	@ResponseStatus(code = HttpStatus.OK)
	public CompletableFuture<Ingredient> updateIngredient(@RequestBody Ingredient ingredient,
			@PathVariable Long idIngredient) {
		return CompletableFuture.supplyAsync(() -> {
			Ingredient currentIngredient = ingredientSvc.getIngredient(idIngredient);
			currentIngredient.setBrand(ingredient.getBrand());
			currentIngredient.setName(ingredient.getName());
			currentIngredient.setMicronutrient(ingredient.getMicronutrient());
			currentIngredient.setMacronutrient(ingredient.getMacronutrient());
			currentIngredient.setSubcategory(ingredient.getSubcategory());
			return ingredientSvc.addIngredient(currentIngredient);
		});
	}

	// Delete a ingredient
	@CircuitBreaker(name = "ingredientBreaker", fallbackMethod = "getVoidCB")
	@TimeLimiter(name = "ingredientBreaker")
	@DeleteMapping("/ingredient/{idIngredient}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public CompletableFuture<Void> deleteIngredient(@PathVariable Long idIngredient) {
		return CompletableFuture.runAsync(() -> ingredientSvc.deleteIngredient(idIngredient));
	}
	
	// Count ingredients
	@CircuitBreaker(name = "ingredientBreaker", fallbackMethod = "getMapIntegerCB")
	@TimeLimiter(name = "ingredientBreaker")
	@GetMapping("/ingredient/count")
	@ResponseStatus(code = HttpStatus.OK)
	public CompletableFuture<Map<String, Integer>> countIngredients(){
		return CompletableFuture.supplyAsync(()-> {
			Map<String, Integer> jsonObject = new HashMap<String, Integer>();
			jsonObject.put("count", ingredientSvc.countIngredients());
			return jsonObject;
		});
	}

	// Get ingredients by category
	@CircuitBreaker(name = "ingredientBreaker", fallbackMethod = "getListObjectCB")
	@TimeLimiter(name = "ingredientBreaker")
	@GetMapping("/ingredient/category/{idCategory}")
	@ResponseStatus(code = HttpStatus.OK)
	public CompletableFuture<List<Ingredient>> getIngredientsByCategory(@PathVariable Long idCategory) {
		return CompletableFuture.supplyAsync(() -> ingredientSvc.getIngredientsByCategory(idCategory));
	}

	// Get ingredients by subcategory
	@CircuitBreaker(name = "ingredientBreaker", fallbackMethod = "getListObjectCB")
	@TimeLimiter(name = "ingredientBreaker")
	@GetMapping("/ingredient/subcategory/{idSubcategory}")
	@ResponseStatus(code = HttpStatus.OK)
	public CompletableFuture<List<Ingredient>> getIngredientsBySubcategory(@PathVariable Long idSubcategory) {
		return CompletableFuture.supplyAsync(() -> ingredientSvc.getIngredientsBySubcategory(idSubcategory));
	}

	// Get ingredient with maximum nutrient
	@CircuitBreaker(name = "ingredientBreaker", fallbackMethod = "getObjectCB")
	@TimeLimiter(name = "ingredientBreaker")
	@GetMapping("/ingredient/nutrient-max/{nutrient}")
	@ResponseStatus(code = HttpStatus.OK)
	public CompletableFuture<Ingredient> getIngredientWithMaxNutrient(@PathVariable String nutrient) {
		return CompletableFuture.supplyAsync(() -> ingredientSvc.getIngredientWithMaxNutrient(nutrient));
	}

	// Get ingredient with minimum nutrient
	@CircuitBreaker(name = "ingredientBreaker", fallbackMethod = "getObjectCB")
	@TimeLimiter(name = "ingredientBreaker")
	@GetMapping("/ingredient/nutrient-min/{nutrient}")
	@ResponseStatus(code = HttpStatus.OK)
	public CompletableFuture<Ingredient> getIngredientWithMinNutrient(@PathVariable String nutrient) {
		return CompletableFuture.supplyAsync(() -> ingredientSvc.getIngredientWithMinNutrient(nutrient));
	}

	// Get quantity to consume by ingredient
	@CircuitBreaker(name = "ingredientBreaker", fallbackMethod = "getMapDecimalCB")
	@TimeLimiter(name = "ingredientBreaker")
	@GetMapping("/ingredient/quantity/{idIngredient}")
	@ResponseStatus(code = HttpStatus.OK)
	public CompletableFuture<Map<String, BigDecimal>> getQuantityToConsume(@PathVariable Long idIngredient) {
		return CompletableFuture.supplyAsync(() -> {
			BigDecimal response = ingredientSvc.getQuantityToConsume(idIngredient);
			Map<String, BigDecimal> jsonObject = new HashMap<String, BigDecimal>();
			jsonObject.put("quantity", response);
			return jsonObject;
		});
	}
	
	// Get ingredients by recipe
	@CircuitBreaker(name = "ingredientBreaker", fallbackMethod = "getListObjectCB")
	@TimeLimiter(name = "ingredientBreaker")
	@GetMapping("/ingredient/recipe/{idRecipeBook}")
	@ResponseStatus(code = HttpStatus.OK)
	public CompletableFuture<List<Ingredient>> getIngredientsByRecipe(@PathVariable Long idRecipeBook) {
		return CompletableFuture.supplyAsync(()-> ingredientSvc.getIngredientsByRecipe(idRecipeBook));
	}
	
	// (CircuitBreaker) Get void circuit breaker
	public CompletableFuture<Void> getVoidCB(Throwable t) {
		return CompletableFuture.runAsync(() -> logger.error("Enabled ingredient breaker" + t));
	}
	
	// (CircuitBreaker) Get object circuit breaker
	public CompletableFuture<Ingredient> getObjectCB(Throwable t) {
		logger.error("Enabled ingredient breaker" + t);
		return CompletableFuture.supplyAsync(() -> {
			Ingredient ingredient = new Ingredient();
			ingredient.setIdIngredient(null);
			ingredient.setBrand(null);
			ingredient.setName(null);
			ingredient.setMacronutrient(null);
			ingredient.setMicronutrient(null);
			ingredient.setSubcategory(null);
			return ingredient;
		});
	}
	
	// (CircuitBreaker) Get list object circuit breaker
	public CompletableFuture<List<Ingredient>> getListObjectCB(Throwable t) {
		logger.error("Enabled ingredient breaker" + t);
		return CompletableFuture.supplyAsync(() -> {
			Ingredient ingredient = new Ingredient();
			List<Ingredient> list = new ArrayList<Ingredient>();
			ingredient.setIdIngredient(null);
			ingredient.setBrand(null);
			ingredient.setName(null);
			ingredient.setMacronutrient(null);
			ingredient.setMicronutrient(null);
			ingredient.setSubcategory(null);
			list.add(ingredient);
			return list;
		});
	}
	
	// (CircuitBreaker) Get map decimal circuit breaker
	public CompletableFuture<Map<String, BigDecimal>> getMapDecimalCB(Throwable t) {
		logger.error("Enabled ingredient breaker " + t);
		return CompletableFuture.supplyAsync(() -> {
			Map<String, BigDecimal> jsonObject = new HashMap<String, BigDecimal>();
			jsonObject.put("idIngredient", null);
			jsonObject.put("brand", null);
			jsonObject.put("name", null);
			jsonObject.put("macronutrient", null);
			jsonObject.put("micronutrient", null);
			jsonObject.put("subcategory", null);
			return jsonObject;
		});
	}
	
	// (CircuitBreaker) Get map integer circuit breaker
	public CompletableFuture<Map<String, Integer>> getMapIntegerCB(Throwable t) {
		logger.error("Enabled ingredient breaker " + t);
		return CompletableFuture.supplyAsync(() -> {
			Map<String, Integer> jsonObject = new HashMap<String, Integer>();
			jsonObject.put("count", null);
			return jsonObject;
		});
	}

//	//Get ingredient with max nutrient
//	@GetMapping("/ingredient/min/{nutrient}")
//	@ResponseStatus(code = HttpStatus.OK)
//	public Map<String, Object> getIngredientWithMinNutrient(@PathVariable String nutrient) {
//	    Object response = ingredientSvc.getIngredientWithMinNutrient(nutrient);
//	    if (response instanceof Object[]) {
//	        Object[] responseData = (Object[]) response;
//	            Map<String, Object> resultMap = new HashMap<>();
//	            resultMap.put("name", responseData[0]);
//	            resultMap.put(nutrient, responseData[1]);
//	            resultMap.put("photo", responseData[2]);
//	            return resultMap;
//	    }
//	    return Collections.emptyMap();
//	}

}
