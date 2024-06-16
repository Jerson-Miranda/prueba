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
			currentIngredient.setBarcode(ingredient.getBarcode());
			currentIngredient.setPhoto(ingredient.getPhoto());
			currentIngredient.setDescription(ingredient.getDescription());
			currentIngredient.setGrMlPza(ingredient.getGrMlPza());
			currentIngredient.setPrice(ingredient.getPrice());
			currentIngredient.setIsFavorite(ingredient.getIsFavorite());
			currentIngredient.setTypeIngredient(ingredient.getTypeIngredient());
			currentIngredient.setCommonIngredient(ingredient.getCommonIngredient());
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
	public CompletableFuture<Map<String, Integer>> countIngredients() {
		return CompletableFuture.supplyAsync(() -> {
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

	// Get ingredient with maximum macronutrient
	@CircuitBreaker(name = "ingredientBreaker", fallbackMethod = "getObjectCB")
	@TimeLimiter(name = "ingredientBreaker")
	@GetMapping("/ingredient/max-macronutrient/{nutrient}")
	@ResponseStatus(code = HttpStatus.OK)
	public CompletableFuture<Ingredient> getIngredientWithMaxNutrient(@PathVariable String nutrient) {
		return CompletableFuture.supplyAsync(() -> ingredientSvc.getIngredientWithMaxNutrient(nutrient));
	}

	// Get ingredient with minimum macronutrient
	@CircuitBreaker(name = "ingredientBreaker", fallbackMethod = "getObjectCB")
	@TimeLimiter(name = "ingredientBreaker")
	@GetMapping("/ingredient/min-macronutrient/{nutrient}")
	@ResponseStatus(code = HttpStatus.OK)
	public CompletableFuture<Ingredient> getIngredientWithMinNutrient(@PathVariable String nutrient) {
		return CompletableFuture.supplyAsync(() -> ingredientSvc.getIngredientWithMinNutrient(nutrient));
	}

	// Get quantity to consume by ingredient
	@CircuitBreaker(name = "ingredientBreaker", fallbackMethod = "getMapDecimalCB")
	@TimeLimiter(name = "ingredientBreaker")
	@GetMapping("/ingredient/quantity/{barcode}")
	@ResponseStatus(code = HttpStatus.OK)
	public CompletableFuture<Map<String, BigDecimal>> getQuantityToConsume(@PathVariable String barcode) {
		return CompletableFuture.supplyAsync(() -> {
			Map<String, BigDecimal> jsonObject = new HashMap<String, BigDecimal>();
			jsonObject.put("quantity", ingredientSvc.getQuantityToConsume(barcode));
			return jsonObject;
		});
	}

	// Get ingredients by recipe
	@CircuitBreaker(name = "ingredientBreaker", fallbackMethod = "getListObjectCB")
	@TimeLimiter(name = "ingredientBreaker")
	@GetMapping("/ingredient/recipe/{idRecipeBook}")
	@ResponseStatus(code = HttpStatus.OK)
	public CompletableFuture<List<Ingredient>> getIngredientsByRecipe(@PathVariable Long idRecipeBook) {
		return CompletableFuture.supplyAsync(() -> ingredientSvc.getIngredientsByRecipe(idRecipeBook));
	}

	// Get ingredients by minimum stock
	@CircuitBreaker(name = "ingredientBreaker", fallbackMethod = "getListObjectCB")
	@TimeLimiter(name = "ingredientBreaker")
	@GetMapping("/ingredient/min-stock/{stock}")
	@ResponseStatus(code = HttpStatus.OK)
	public CompletableFuture<List<Ingredient>> getIngredientsByMinStock(@PathVariable Integer stock) {
		return CompletableFuture.supplyAsync(() -> ingredientSvc.getIngredientsByMinStock(stock));
	}

	// Get favorite ingredients
	@CircuitBreaker(name = "ingredientBreaker", fallbackMethod = "getListObjectCB")
	@TimeLimiter(name = "ingredientBreaker")
	@GetMapping("/ingredient/favorite")
	@ResponseStatus(code = HttpStatus.OK)
	public CompletableFuture<List<Ingredient>> getFavoriteIngredients() {
		return CompletableFuture.supplyAsync(() -> ingredientSvc.getFavoriteIngredients());
	}

	// Get ingredients by diet
	@CircuitBreaker(name = "ingredientBreaker", fallbackMethod = "getListMapObjectCB")
	@TimeLimiter(name = "ingredientBreaker")
	@GetMapping("/ingredient/diet/{idDiet}")
	@ResponseStatus(code = HttpStatus.OK)
	public CompletableFuture<List<Map<String, Object>>> getIngredientsByDiet(@PathVariable Long idDiet) {
		return CompletableFuture.supplyAsync(() -> {
			List<Object> response = ingredientSvc.getIngredientsByDiet(idDiet);
			List<Map<String, Object>> json = new ArrayList<Map<String, Object>>();
			for (Object obj : response) {
				Object[] res = (Object[]) obj;
				Map<String, Object> jsonObject = new HashMap<String, Object>();
				jsonObject.put("idIngredient", res[0]);
				jsonObject.put("barcode", res[1]);
				jsonObject.put("name", res[2]);
				jsonObject.put("brand", res[3]);
				jsonObject.put("grMlPza", res[4]);
				jsonObject.put("amount", res[5]);
				jsonObject.put("price", res[6]);
				json.add(jsonObject);
			}
			return json;
		});
	}

	// Get ingredients by diet between dates
	@CircuitBreaker(name = "ingredientBreaker", fallbackMethod = "getListMapObjectCB")
	@TimeLimiter(name = "ingredientBreaker")
	@GetMapping("/ingredient/diet/{startDate}/{endDate}")
	@ResponseStatus(code = HttpStatus.OK)
	public CompletableFuture<List<Map<String, Object>>> getIngredientsByDietRange(@PathVariable String startDate,
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
			List<Object> response = ingredientSvc.getIngredientsByDietRange(startDate1, endDate1);
			List<Map<String, Object>> json = new ArrayList<Map<String, Object>>();
			for (Object obj : response) {
				Object[] res = (Object[]) obj;
				Map<String, Object> jsonObject = new HashMap<String, Object>();
				jsonObject.put("idIngredient", res[0]);
				jsonObject.put("barcode", res[1]);
				jsonObject.put("name", res[2]);
				jsonObject.put("brand", res[3]);
				jsonObject.put("grMlPza", res[4]);
				jsonObject.put("amount", res[5]);
				jsonObject.put("price", res[6]);
				json.add(jsonObject);
			}
			return json;
		});
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
			ingredient.setBarcode(null);
			ingredient.setPhoto(null);
			ingredient.setDescription(null);
			ingredient.setGrMlPza(null);
			ingredient.setPrice(null);
			ingredient.setIsFavorite(null);
			ingredient.setTypeIngredient(null);
			ingredient.setCommonIngredient(null);
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
			ingredient.setBarcode(null);
			ingredient.setPhoto(null);
			ingredient.setDescription(null);
			ingredient.setGrMlPza(null);
			ingredient.setPrice(null);
			ingredient.setIsFavorite(null);
			ingredient.setTypeIngredient(null);
			ingredient.setCommonIngredient(null);
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
			jsonObject.put("barcode", null);
			jsonObject.put("photo", null);
			jsonObject.put("description", null);
			jsonObject.put("grMlPza", null);
			jsonObject.put("price", null);
			jsonObject.put("isFavorite", null);
			jsonObject.put("typeIngredient", null);
			jsonObject.put("commonIngredient", null);
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

	// (CircuitBreaker) Get list map object circuit breaker
	public CompletableFuture<List<Map<String, Object>>> getListMapObjectCB(Throwable t) {
		logger.error("Enabled ingredient breaker " + t);
		return CompletableFuture.supplyAsync(() -> {
			List<Map<String, Object>> json = new ArrayList<Map<String, Object>>();
			Map<String, Object> jsonObject = new HashMap<String, Object>();
			jsonObject.put("idIngredient", null);
			jsonObject.put("name", null);
			jsonObject.put("brand", null);
			jsonObject.put("grMlPza", null);
			jsonObject.put("amount", null);
			jsonObject.put("price", null);
			json.add(jsonObject);
			return json;
		});
	}

}
