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
import com.evenjoin.diet_ms.entity.Diet;
import com.evenjoin.diet_ms.services.DietSvc;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;

@RefreshScope
@RestController
@RequestMapping("/diet")
public class DietCtrl {

	private static final Logger logger = LoggerFactory.getLogger(DietCtrl.class);

	@Autowired
	private DietSvc dietSvc;

	// Get all diets
	@CircuitBreaker(name = "dietBreaker", fallbackMethod = "getListObjectCB")
	@TimeLimiter(name = "dietBreaker")
	@GetMapping("/all")
	@ResponseStatus(code = HttpStatus.OK)
	public CompletableFuture<List<Diet>> getDiets() {
		return CompletableFuture.supplyAsync(() -> dietSvc.getDiets());
	}

	// Get a diet
	@CircuitBreaker(name = "dietBreaker", fallbackMethod = "getObjectCB")
	@TimeLimiter(name = "dietBreaker")
	@GetMapping("/{idDiet}")
	@ResponseStatus(code = HttpStatus.OK)
	public CompletableFuture<Diet> getDiet(@PathVariable Long idDiet) {
		return CompletableFuture.supplyAsync(() -> dietSvc.getDiet(idDiet));
	}

	// Add a diet
	@CircuitBreaker(name = "dietBreaker", fallbackMethod = "getObjectCB")
	@TimeLimiter(name = "dietBreaker")
	@PostMapping("/add")
	@ResponseStatus(code = HttpStatus.CREATED)
	public CompletableFuture<Diet> addDiet(@RequestBody Diet diet) {
		return CompletableFuture.supplyAsync(() -> dietSvc.addDiet(diet));
	}

	// Update a diet
	@CircuitBreaker(name = "dietBreaker", fallbackMethod = "getObjectCB")
	@TimeLimiter(name = "dietBreaker")
	@PutMapping("/update/{idDiet}")
	@ResponseStatus(code = HttpStatus.OK)
	public CompletableFuture<Diet> updateDiet(@RequestBody Diet diet, @PathVariable Long idDiet) {
		return CompletableFuture.supplyAsync(() -> {
			Diet currentDiet = dietSvc.getDiet(idDiet);
			currentDiet.setDate(diet.getDate());
			return dietSvc.addDiet(currentDiet);
		});
	}

	// Delete a diet
	@CircuitBreaker(name = "dietBreaker", fallbackMethod = "getVoidCB")
	@TimeLimiter(name = "dietBreaker")
	@DeleteMapping("/{idDiet}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public CompletableFuture<Void> deleteDiet(@PathVariable Long idDiet) {
		return CompletableFuture.runAsync(() -> dietSvc.deleteDiet(idDiet));
	}

	// (CircuitBreaker) Get void circuit breaker
	public CompletableFuture<Void> getVoidCB(Throwable t) {
		return CompletableFuture.runAsync(() -> logger.error("Enabled diet breaker" + t));
	}

	// (CircuitBreaker) Get object circuit breaker
	public CompletableFuture<Diet> getObjectCB(Throwable t) {
		logger.error("Enabled diet breaker" + t);
		return CompletableFuture.supplyAsync(() -> {
			Diet diet = new Diet();
			diet.setIdDite(null);
			diet.setDate(null);
			return diet;
		});
	}

	// (CircuitBreaker) Get list object circuit breaker
	public CompletableFuture<List<Diet>> getListObjectCB(Throwable t) {
		logger.error("Enabled diet breaker" + t);
		return CompletableFuture.supplyAsync(() -> {
			Diet diet = new Diet();
			List<Diet> list = new ArrayList<Diet>();
			diet.setIdDite(null);
			diet.setDate(null);
			list.add(diet);
			return list;
		});
	}

}
