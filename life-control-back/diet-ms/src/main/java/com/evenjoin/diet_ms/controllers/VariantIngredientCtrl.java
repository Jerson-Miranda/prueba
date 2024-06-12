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
import com.evenjoin.diet_ms.entity.VariantIngredient;
import com.evenjoin.diet_ms.services.VariantIngredientSvc;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;

@RefreshScope
@RestController
@RequestMapping("/diet")
public class VariantIngredientCtrl {

	private static final Logger logger = LoggerFactory.getLogger(VariantIngredient.class);

	@Autowired
	private VariantIngredientSvc variantIngredientSvc;

	// Get all variant ingredients
	@CircuitBreaker(name = "variantIngredientBreaker", fallbackMethod = "getListObjectCB")
	@TimeLimiter(name = "variantIngredientBreaker")
	@GetMapping("/variant-ingredient/all")
	@ResponseStatus(code = HttpStatus.OK)
	public CompletableFuture<List<VariantIngredient>> getVariantIngredients() {
		return CompletableFuture.supplyAsync(() -> variantIngredientSvc.getVariantIngredients());
	}

	// Get a variant ingredient
	@CircuitBreaker(name = "variantIngredientBreaker", fallbackMethod = "getObjectCB")
	@TimeLimiter(name = "variantIngredientBreaker")
	@GetMapping("/variant-ingredient/{idVariantIngredient}")
	@ResponseStatus(code = HttpStatus.OK)
	public CompletableFuture<VariantIngredient> getVariantIngredient(@PathVariable Long idVariantIngredient) {
		return CompletableFuture.supplyAsync(() -> variantIngredientSvc.getVariantIngredient(idVariantIngredient));
	}

	// Add a variant ingredient
	@CircuitBreaker(name = "variantIngredientBreaker", fallbackMethod = "getObjectCB")
	@TimeLimiter(name = "variantIngredientBreaker")
	@PostMapping("/variant-ingredient/add")
	@ResponseStatus(code = HttpStatus.CREATED)
	public CompletableFuture<VariantIngredient> addVariantIngredient(@RequestBody VariantIngredient variantIngredient) {
		return CompletableFuture.supplyAsync(() -> variantIngredientSvc.addVariantIngredient(variantIngredient));
	}

	// Update a variant ingredient
	@CircuitBreaker(name = "variantIngredientBreaker", fallbackMethod = "getObjectCB")
	@TimeLimiter(name = "variantIngredientBreaker")
	@PutMapping("/variant-ingredient/update/{idVariantIngredient}")
	@ResponseStatus(code = HttpStatus.OK)
	public CompletableFuture<VariantIngredient> updateVariantIngredient(
			@RequestBody VariantIngredient variantIngredient, @PathVariable Long idVariantIngredient) {
		return CompletableFuture.supplyAsync(() -> {
			VariantIngredient currentVariantIngredient = variantIngredientSvc.getVariantIngredient(idVariantIngredient);
			currentVariantIngredient.setBarcode(variantIngredient.getBarcode());
			currentVariantIngredient.setPhoto(variantIngredient.getPhoto());
			currentVariantIngredient.setDescription(variantIngredient.getDescription());
			currentVariantIngredient.setGrMlPza(variantIngredient.getGrMlPza());
			currentVariantIngredient.setPrice(variantIngredient.getPrice());
			currentVariantIngredient.setIsFavorite(variantIngredient.getIsFavorite());
			currentVariantIngredient.setTypeIngredient(variantIngredient.getTypeIngredient());
			currentVariantIngredient.setIngredient(variantIngredient.getIngredient());
			return variantIngredientSvc.addVariantIngredient(currentVariantIngredient);
		});
	}

	// Delete a variant ingredient
	@CircuitBreaker(name = "variantIngredientBreaker", fallbackMethod = "getVoidCB")
	@TimeLimiter(name = "variantIngredientBreaker")
	@DeleteMapping("/variant-ingredient/{idVariantIngredient}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public CompletableFuture<Void> deleteVariantIngredient(@PathVariable Long idVariantIngredient) {
		return CompletableFuture.runAsync(() -> variantIngredientSvc.deleteVariantIngredient(idVariantIngredient));
	}
	
	// Get variant ingredients by minimum stock
	@CircuitBreaker(name = "variantIngredientBreaker", fallbackMethod = "getListObjectCB")
	@TimeLimiter(name = "variantIngredientBreaker")
	@GetMapping("/variant-ingredient/stock-min/{stock}")
	@ResponseStatus(code = HttpStatus.OK)
	public CompletableFuture<List<VariantIngredient>> getIngredientsByMinStock(@PathVariable Integer stock) {
		return CompletableFuture.supplyAsync(() -> variantIngredientSvc.getIngredientsByMinStock(stock));
	}

	// (CircuitBreaker) Get void circuit breaker
	public CompletableFuture<Void> getVoidCB(Throwable t) {
		return CompletableFuture.runAsync(() -> logger.error("Enabled variant ingredient breaker" + t));
	}

	// (CircuitBreaker) Get object circuit breaker
	public CompletableFuture<VariantIngredient> getObjectCB(Throwable t) {
		logger.error("Enabled variant ingredient breaker" + t);
		return CompletableFuture.supplyAsync(() -> {
			VariantIngredient variantIngredient = new VariantIngredient();
			variantIngredient.setIdVariantIngredient(null);
			variantIngredient.setBarcode(null);
			variantIngredient.setPhoto(null);
			variantIngredient.setDescription(null);
			variantIngredient.setGrMlPza(null);
			variantIngredient.setPrice(null);
			variantIngredient.setIsFavorite(null);
			variantIngredient.setTypeIngredient(null);
			variantIngredient.setIngredient(null);
			return variantIngredient;
		});
	}

	// (CircuitBreaker) Get list object circuit breaker
	public CompletableFuture<List<VariantIngredient>> getListObjectCB(Throwable t) {
		logger.error("Enabled variant ingredient breaker" + t);
		return CompletableFuture.supplyAsync(() -> {
			List<VariantIngredient> list = new ArrayList<VariantIngredient>();
			VariantIngredient variantIngredient = new VariantIngredient();
			variantIngredient.setIdVariantIngredient(null);
			variantIngredient.setBarcode(null);
			variantIngredient.setPhoto(null);
			variantIngredient.setDescription(null);
			variantIngredient.setGrMlPza(null);
			variantIngredient.setPrice(null);
			variantIngredient.setIsFavorite(null);
			variantIngredient.setTypeIngredient(null);
			variantIngredient.setIngredient(null);
			list.add(variantIngredient);
			return list;
		});
	}

}
