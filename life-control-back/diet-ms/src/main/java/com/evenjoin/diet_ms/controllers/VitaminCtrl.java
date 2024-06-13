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
import com.evenjoin.diet_ms.entity.Vitamin;
import com.evenjoin.diet_ms.services.VitaminSvc;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;

@RefreshScope
@RestController
@RequestMapping("/diet")
public class VitaminCtrl {

	private static final Logger logger = LoggerFactory.getLogger(VitaminCtrl.class);

	@Autowired
	private VitaminSvc vitaminSvc;

	// Get all vitamins
	@CircuitBreaker(name = "vitaminBreaker", fallbackMethod = "getListObjectCB")
	@GetMapping("/vitamin/all")
	@ResponseStatus(code = HttpStatus.OK)
	public CompletableFuture<List<Vitamin>> getCategories() {
		return CompletableFuture.supplyAsync(() -> vitaminSvc.getVitamins());
	}

	// Get a vitamin
	@CircuitBreaker(name = "vitaminBreaker", fallbackMethod = "getObjectCB")
	@TimeLimiter(name = "vitaminBreaker")
	@GetMapping("/vitamin/{idVitamin}")
	@ResponseStatus(code = HttpStatus.OK)
	public CompletableFuture<Vitamin> getVitamin(@PathVariable Long idVitamin) throws InterruptedException {
		return CompletableFuture.supplyAsync(() -> vitaminSvc.getVitamin(idVitamin));
	}

	// Add a vitamin
	@CircuitBreaker(name = "vitaminBreaker", fallbackMethod = "getObjectCB")
	@TimeLimiter(name = "vitaminBreaker")
	@PostMapping("/vitamin/add")
	@ResponseStatus(code = HttpStatus.CREATED)
	public CompletableFuture<Vitamin> addVitamin(@RequestBody Vitamin vitamin) {
		return CompletableFuture.supplyAsync(() -> vitaminSvc.addVitamin(vitamin));
	}

	// Update a vitamin
	@CircuitBreaker(name = "vitaminBreaker", fallbackMethod = "getObjectCB")
	@TimeLimiter(name = "vitaminBreaker")
	@PutMapping("/vitamin/update/{idVitamin}")
	@ResponseStatus(code = HttpStatus.OK)
	public CompletableFuture<Vitamin> updateVitamin(@RequestBody Vitamin vitamin, @PathVariable Long idVitamin) {
		return CompletableFuture.supplyAsync(() -> {
			Vitamin currentVitamin = vitaminSvc.getVitamin(idVitamin);
			currentVitamin.setVitaminA(vitamin.getVitaminA());
			currentVitamin.setVitaminB(vitamin.getVitaminB());
			currentVitamin.setVitaminC(vitamin.getVitaminC());
			currentVitamin.setVitaminD(vitamin.getVitaminD());
			currentVitamin.setVitaminE(vitamin.getVitaminE());
			currentVitamin.setVitaminK(vitamin.getVitaminK());
			return vitaminSvc.addVitamin(currentVitamin);
		});
	}

	// Delete a vitamin
	@CircuitBreaker(name = "vitaminBreaker", fallbackMethod = "getVoidCB")
	@TimeLimiter(name = "vitaminBreaker")
	@DeleteMapping("/vitamin/{idVitamin}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public CompletableFuture<Void> deleteVitamin(@PathVariable Long idVitamin) {
		return CompletableFuture.runAsync(() -> vitaminSvc.deleteVitamin(idVitamin));
	}
	
	// Get vitamins by ingredient
	@CircuitBreaker(name = "vitaminBreaker", fallbackMethod = "getObjectCB")
	@TimeLimiter(name = "vitaminBreaker")
	@GetMapping("/vitamin/ingredient/{barcode}")
	@ResponseStatus(code = HttpStatus.OK)
	public CompletableFuture<Vitamin> getVitaminsByIngredient(@PathVariable String barcode) {
		return CompletableFuture.supplyAsync(() -> vitaminSvc.getVitaminsByIngredient(barcode));
	}

	// (CircuitBreaker) Get void circuit breaker
	public CompletableFuture<Void> getVoidCB(Throwable t) {
		return CompletableFuture.runAsync(() -> logger.error("Error while deleting " + t));
	}

	// (CircuitBreaker) Get object circuit breaker
	public CompletableFuture<Vitamin> getObjectCB(Throwable t) {
		logger.error("Enabled vitamin breaker " + t);
		return CompletableFuture.supplyAsync(() -> {
			Vitamin vitamin = new Vitamin();
			vitamin.setIdVitamin(null);
			vitamin.setVitaminA(null);
			vitamin.setVitaminB(null);
			vitamin.setVitaminC(null);
			vitamin.setVitaminD(null);
			vitamin.setVitaminE(null);
			vitamin.setVitaminK(null);
			return vitamin;
		});
	}

	// (CircuitBreaker) Get list object circuit breaker
	public CompletableFuture<List<Vitamin>> getListObjectCB(Throwable t) {
		logger.error("Enabled vitamin breaker " + t);
		return CompletableFuture.supplyAsync(() -> {
			Vitamin vitamin = new Vitamin();
			List<Vitamin> list = new ArrayList<Vitamin>();
			vitamin.setIdVitamin(null);
			vitamin.setVitaminA(null);
			vitamin.setVitaminB(null);
			vitamin.setVitaminC(null);
			vitamin.setVitaminD(null);
			vitamin.setVitaminE(null);
			vitamin.setVitaminK(null);
			list.add(vitamin);
			return list;
		});
	}

}
