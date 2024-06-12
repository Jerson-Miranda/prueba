package com.evenjoin.diet_ms.controllers;

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
import com.evenjoin.diet_ms.entity.Pantry;
import com.evenjoin.diet_ms.services.PantrySvc;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;

@RefreshScope
@RestController
@RequestMapping("/diet")
public class PantryCtrl {

	private static final Logger logger = LoggerFactory.getLogger(Pantry.class);

	@Autowired
	private PantrySvc pantrySvc;

	// Get all pantries
	@CircuitBreaker(name = "pantryBreaker", fallbackMethod = "getListObjectCB")
	@TimeLimiter(name = "pantryBreaker")
	@GetMapping("/pantry/all")
	@ResponseStatus(code = HttpStatus.OK)
	public CompletableFuture<List<Pantry>> getPantries() {
		return CompletableFuture.supplyAsync(() -> pantrySvc.getPantries());
	}

	// Get a pantry
	@CircuitBreaker(name = "pantryBreaker", fallbackMethod = "getObjectCB")
	@TimeLimiter(name = "pantryBreaker")
	@GetMapping("/pantry/{idPantry}")
	@ResponseStatus(code = HttpStatus.OK)
	public CompletableFuture<Pantry> getPantry(@PathVariable Long idPantry) {
		return CompletableFuture.supplyAsync(() -> pantrySvc.getPantry(idPantry));
	}

	// Add a pantry
	@CircuitBreaker(name = "pantryBreaker", fallbackMethod = "getObjectCB")
	@TimeLimiter(name = "pantryBreaker")
	@PostMapping("/pantry/add")
	@ResponseStatus(code = HttpStatus.CREATED)
	public CompletableFuture<Pantry> addPantry(@RequestBody Pantry pantry) {
		return CompletableFuture.supplyAsync(() -> pantrySvc.addPantry(pantry));
	}

	// Update a pantry
	@CircuitBreaker(name = "pantryBreaker", fallbackMethod = "getObjectCB")
	@TimeLimiter(name = "pantryBreaker")
	@PutMapping("/pantry/update/{idPantry}")
	@ResponseStatus(code = HttpStatus.OK)
	public CompletableFuture<Pantry> updatePantry(@RequestBody Pantry pantry, @PathVariable Long idPantry) {
		return CompletableFuture.supplyAsync(() -> {
			Pantry currentPantry = pantrySvc.getPantry(idPantry);
			currentPantry.setExpirationDate(pantry.getExpirationDate());
			currentPantry.setStock(pantry.getStock());
			currentPantry.setIngredient(pantry.getIngredient());
			return pantrySvc.addPantry(currentPantry);
		});
	}

	// Delete a pantry
	@CircuitBreaker(name = "pantryBreaker", fallbackMethod = "getVoidCB")
	@TimeLimiter(name = "pantryBreaker")
	@DeleteMapping("/pantry/{idPantry}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public CompletableFuture<Void> deletePantry(@PathVariable Long idPantry) {
		return CompletableFuture.runAsync(() -> pantrySvc.deletePantry(idPantry));
	}
	
	// Get expiration date by ingredient
	@CircuitBreaker(name = "pantryBreaker", fallbackMethod = "getMapDateCB")
	@TimeLimiter(name = "pantryBreaker")
	@GetMapping("/pantry/expiration-date/{barcode}")
	@ResponseStatus(code = HttpStatus.OK)
	public CompletableFuture<Map<String, Date>> getExpirationDateByIngredient(@PathVariable String barcode) {
		return CompletableFuture.supplyAsync(() -> {
			Map<String, Date> jsonObject = new HashMap<String, Date>(); 
			jsonObject.put("expiration-date", pantrySvc.getExpirationDateByIngredient(barcode));
			return jsonObject;
		});
	}
	
	// Get stock by ingredient
	@CircuitBreaker(name = "pantryBreaker", fallbackMethod = "getMapIntegerCB")
	@TimeLimiter(name = "pantryBreaker")
	@GetMapping("/pantry/stock/{barcode}")
	@ResponseStatus(code = HttpStatus.OK)
	public CompletableFuture<Map<String, Integer>> getStockByIngredient(@PathVariable String barcode) {
		return CompletableFuture.supplyAsync(() -> {
			Map<String, Integer> jsonObject = new HashMap<String, Integer>(); 
			jsonObject.put("stock", pantrySvc.getStockByIngredient(barcode));
			return jsonObject;
		});
	}
	
	// (CircuitBreaker) Get void circuit breaker
	public CompletableFuture<Void> getVoidCB(Throwable t) {
		return CompletableFuture.runAsync(() -> logger.error("Enabled pantry breaker" + t));
	}

	// (CircuitBreaker) Get object circuit breaker
	public CompletableFuture<Pantry> getObjectCB(Throwable t) {
		logger.error("Enabled pantry breaker" + t);
		return CompletableFuture.supplyAsync(() -> {
			Pantry pantry = new Pantry();
			pantry.setIdPantry(null);
			pantry.setExpirationDate(null);
			pantry.setStock(0);
			pantry.setIngredient(null);
			return pantry;
		});
	}

	// (CircuitBreaker) Get list object circuit breaker
	public CompletableFuture<List<Pantry>> getListObjectCB(Throwable t) {
		logger.error("Enabled pantry breaker" + t);
		return CompletableFuture.supplyAsync(() -> {
			List<Pantry> list = new ArrayList<Pantry>();
			Pantry pantry = new Pantry();
			pantry.setIdPantry(null);
			pantry.setExpirationDate(null);
			pantry.setStock(0);
			pantry.setIngredient(null);
			list.add(pantry);
			return list;
		});
	}
	
	// (CircuitBreaker) Get map date circuit breaker
	public CompletableFuture<Map<String, Date>> getMapDateCB(Throwable t) {
		logger.error("Enabled pantry breaker " + t);
		return CompletableFuture.supplyAsync(() -> {
			Map<String, Date> jsonObject = new HashMap<String, Date>();
			jsonObject.put("expiration-date", null);
			return jsonObject;
		});
	}
	
	// (CircuitBreaker) Get map integer circuit breaker
	public CompletableFuture<Map<String, Integer>> getMapIntegerCB(Throwable t) {
		logger.error("Enabled pantry breaker " + t);
		return CompletableFuture.supplyAsync(() -> {
			Map<String, Integer> jsonObject = new HashMap<String, Integer>();
			jsonObject.put("stock", null);
			return jsonObject;
		});
	}
}
