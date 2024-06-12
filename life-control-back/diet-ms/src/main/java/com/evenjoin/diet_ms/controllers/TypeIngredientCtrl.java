package com.evenjoin.diet_ms.controllers;

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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.evenjoin.diet_ms.entity.TypeIngredient;
import com.evenjoin.diet_ms.services.TypeIngredientSvc;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;

@RefreshScope
@RestController
@RequestMapping("/diet")
public class TypeIngredientCtrl {

	private static final Logger logger = LoggerFactory.getLogger(TypeIngredient.class);

	@Autowired
	private TypeIngredientSvc typeIngredientSvc;

	// Get all type ingredients
	@CircuitBreaker(name = "typeIngredientBreaker", fallbackMethod = "getListObjectCB")
	@TimeLimiter(name = "typeIngredientBreaker")
	@GetMapping("/type-ingredient/all")
	@ResponseStatus(code = HttpStatus.OK)
	public CompletableFuture<List<TypeIngredient>> getTypeIngredients() {
		return CompletableFuture.supplyAsync(() -> typeIngredientSvc.getTypeIngredients());
	}

	// Get a type ingredient
	@CircuitBreaker(name = "typeIngredientBreaker", fallbackMethod = "getObjectCB")
	@TimeLimiter(name = "typeIngredientBreaker")
	@GetMapping("/type-ingredient/{idTypeIngredient}")
	@ResponseStatus(code = HttpStatus.OK)
	public CompletableFuture<TypeIngredient> getTypeIngredient(@PathVariable Long idTypeIngredient) {
		return CompletableFuture.supplyAsync(() -> typeIngredientSvc.getTypeIngredient(idTypeIngredient));
	}

	// Add a type ingredient
	@CircuitBreaker(name = "typeIngredientBreaker", fallbackMethod = "getObjectCB")
	@TimeLimiter(name = "typeIngredientBreaker")
	@PostMapping("/type-ingredient/add")
	@ResponseStatus(code = HttpStatus.CREATED)
	public CompletableFuture<TypeIngredient> addTypeIngredient(@RequestBody TypeIngredient typeIngredient) {
		return CompletableFuture.supplyAsync(() -> typeIngredientSvc.addTypeIngredient(typeIngredient));
	}

	// Update a type ingredient
	@CircuitBreaker(name = "typeIngredientBreaker", fallbackMethod = "getObjectCB")
	@TimeLimiter(name = "typeIngredientBreaker")
	@PutMapping("/type-ingredient/update/{idTypeIngredient}")
	@ResponseStatus(code = HttpStatus.OK)
	public CompletableFuture<TypeIngredient> updateTypeIngredient(@RequestBody TypeIngredient typeIngredient,
			@PathVariable Long idTypeIngredient) {
		return CompletableFuture.supplyAsync(() -> {
			TypeIngredient currentTypeIngredient = typeIngredientSvc.getTypeIngredient(idTypeIngredient);
			currentTypeIngredient.setName(typeIngredient.getName());
			return typeIngredientSvc.addTypeIngredient(currentTypeIngredient);
		});
	}

	// Delete a type ingredient
	@CircuitBreaker(name = "typeIngredientBreaker", fallbackMethod = "getVoidCB")
	@TimeLimiter(name = "typeIngredientBreaker")
	@DeleteMapping("/type-ingredient/{idTypeIngredient}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public CompletableFuture<Void> deleteTypeIngredient(@PathVariable Long idTypeIngredient) {
		return CompletableFuture.runAsync(() -> typeIngredientSvc.deleteTypeIngredient(idTypeIngredient));
	}
	
	// Count type ingredients
	@CircuitBreaker(name = "typeIngredientBreaker", fallbackMethod = "getMapIntegerCB")
	@TimeLimiter(name = "typeIngredientBreaker")
	@DeleteMapping("/type-ingredient/count")
	@ResponseStatus(code = HttpStatus.OK)
	public CompletableFuture<Map<String, Integer>> countTypeIngredients() {
		return CompletableFuture.supplyAsync(() -> {
			Map<String, Integer> jsonObject = new HashMap<String, Integer>();
			jsonObject.put("count", typeIngredientSvc.countTypeIngredients());
			return jsonObject;
		});
	}

	// (CircuitBreaker) Get void circuit breaker
	public CompletableFuture<Void> getVoidCB(Throwable t) {
		return CompletableFuture.runAsync(() -> logger.error("Enabled type ingredient breaker" + t));
	}

	// (CircuitBreaker) Get object circuit breaker
	public CompletableFuture<TypeIngredient> getObjectCB(Throwable t) {
		logger.error("Enabled type ingredient breaker" + t);
		return CompletableFuture.supplyAsync(() -> {
			TypeIngredient typeIngredient = new TypeIngredient();
			typeIngredient.setIdTypeIngredient(null);
			typeIngredient.setName(null);
			return typeIngredient;
		});
	}

	// (CircuitBreaker) Get list object circuit breaker
	public CompletableFuture<List<TypeIngredient>> getListObjectCB(Throwable t) {
		logger.error("Enabled type ingredient breaker" + t);
		return CompletableFuture.supplyAsync(() -> {
			List<TypeIngredient> list = new ArrayList<TypeIngredient>();
			TypeIngredient typeIngredient = new TypeIngredient();
			typeIngredient.setIdTypeIngredient(null);
			typeIngredient.setName(null);
			list.add(typeIngredient);
			return list;
		});
	}
	
	// (CircuitBreaker) Get list map integer circuit breaker
	public CompletableFuture<Map<String, Integer>> getMapIntegerCB(Throwable t) {
		logger.error("Enabled type ingredient breaker " + t);
		return CompletableFuture.supplyAsync(() -> {
			Map<String, Integer> jsonObject = new HashMap<String, Integer>();
			jsonObject.put("count", null);
			return jsonObject;
		});
	}
}
