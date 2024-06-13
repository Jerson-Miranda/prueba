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

import com.evenjoin.diet_ms.entity.CommonIngredient;
import com.evenjoin.diet_ms.services.CommonIngredientSvc;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;

@RefreshScope
@RestController
@RequestMapping("/diet")
public class CommonIngredientCtrl {

	private static final Logger logger = LoggerFactory.getLogger(CommonIngredient.class);

	@Autowired
	private CommonIngredientSvc commonIngredientSvc;

	// Get all common ingredients
	@CircuitBreaker(name = "commonIngredientBreaker", fallbackMethod = "getListObjectCB")
	@TimeLimiter(name = "commonIngredientBreaker")
	@GetMapping("/common-ingredient/all")
	@ResponseStatus(code = HttpStatus.OK)
	public CompletableFuture<List<CommonIngredient>> getCommonIngredients() {
		return CompletableFuture.supplyAsync(() -> commonIngredientSvc.getCommonIngredients());
	}

	// Get a common ingredient
	@CircuitBreaker(name = "commonIngredientBreaker", fallbackMethod = "getObjectCB")
	@TimeLimiter(name = "commonIngredientBreaker")
	@GetMapping("/common-ingredient/{idCommonIngredient}")
	@ResponseStatus(code = HttpStatus.OK)
	public CompletableFuture<CommonIngredient> getCommonIngredient(@PathVariable Long idCommonIngredient) {
		return CompletableFuture.supplyAsync(() -> commonIngredientSvc.getCommonIngredient(idCommonIngredient));
	}

	// Add a common ingredient
	@CircuitBreaker(name = "commonIngredientBreaker", fallbackMethod = "getObjectCB")
	@TimeLimiter(name = "commonIngredientBreaker")
	@PostMapping("/common-ingredient/add")
	@ResponseStatus(code = HttpStatus.CREATED)
	public CompletableFuture<CommonIngredient> addCommonIngredient(@RequestBody CommonIngredient commonIngredient) {
		return CompletableFuture.supplyAsync(() -> commonIngredientSvc.addCommonIngredient(commonIngredient));
	}

	// Update a common ingredient
	@CircuitBreaker(name = "commonIngredientBreaker", fallbackMethod = "getObjectCB")
	@TimeLimiter(name = "commonIngredientBreaker")
	@PutMapping("/common-ingredient/update/{idCommonIngredient}")
	@ResponseStatus(code = HttpStatus.OK)
	public CompletableFuture<CommonIngredient> updateCommonIngredient(
			@RequestBody CommonIngredient commonIngredient, @PathVariable Long idCommonIngredient) {
		return CompletableFuture.supplyAsync(() -> {
			CommonIngredient currentCommonIngredient = commonIngredientSvc.getCommonIngredient(idCommonIngredient);
			currentCommonIngredient.setBrand(commonIngredient.getBrand());
			currentCommonIngredient.setName(commonIngredient.getName());
			currentCommonIngredient.setMicronutrient(commonIngredient.getMicronutrient());
			currentCommonIngredient.setMacronutrient(commonIngredient.getMacronutrient());
			currentCommonIngredient.setSubcategory(commonIngredient.getSubcategory());
			return commonIngredientSvc.addCommonIngredient(currentCommonIngredient);
		});
	}

	// Delete a common ingredient
	@CircuitBreaker(name = "commonIngredientBreaker", fallbackMethod = "getVoidCB")
	@TimeLimiter(name = "commonIngredientBreaker")
	@DeleteMapping("/common-ingredient/{idCommonIngredient}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public CompletableFuture<Void> deleteCommonIngredient(@PathVariable Long idCommonIngredient) {
		return CompletableFuture.runAsync(() -> commonIngredientSvc.deleteCommonIngredient(idCommonIngredient));
	}

	// (CircuitBreaker) Get void circuit breaker
	public CompletableFuture<Void> getVoidCB(Throwable t) {
		return CompletableFuture.runAsync(() -> logger.error("Enabled common ingredient breaker" + t));
	}

	// (CircuitBreaker) Get object circuit breaker
	public CompletableFuture<CommonIngredient> getObjectCB(Throwable t) {
		logger.error("Enabled common ingredient breaker" + t);
		return CompletableFuture.supplyAsync(() -> {
			CommonIngredient commonIngredient = new CommonIngredient();
			commonIngredient.setIdCommonIngredient(null);
			commonIngredient.setBrand(null);
			commonIngredient.setName(null);
			commonIngredient.setMacronutrient(null);
			commonIngredient.setMicronutrient(null);
			commonIngredient.setSubcategory(null);
			return commonIngredient;
		});
	}

	// (CircuitBreaker) Get list object circuit breaker
	public CompletableFuture<List<CommonIngredient>> getListObjectCB(Throwable t) {
		logger.error("Enabled common ingredient breaker" + t);
		return CompletableFuture.supplyAsync(() -> {
			List<CommonIngredient> list = new ArrayList<CommonIngredient>();
			CommonIngredient commonIngredient = new CommonIngredient();
			commonIngredient.setIdCommonIngredient(null);
			commonIngredient.setBrand(null);
			commonIngredient.setName(null);
			commonIngredient.setMacronutrient(null);
			commonIngredient.setMicronutrient(null);
			commonIngredient.setSubcategory(null);
			list.add(commonIngredient);
			return list;
		});
	}

}
