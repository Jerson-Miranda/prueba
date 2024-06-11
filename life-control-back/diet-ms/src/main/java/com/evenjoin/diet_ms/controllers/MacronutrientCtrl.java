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
import com.evenjoin.diet_ms.entity.Macronutrient;
import com.evenjoin.diet_ms.services.MacronutrientSvc;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;

@RefreshScope
@RestController
@RequestMapping("/diet")
public class MacronutrientCtrl {

	private static final Logger logger = LoggerFactory.getLogger(MacronutrientCtrl.class);

	@Autowired
	private MacronutrientSvc macronutrientSvc;

	// Get all macronutrient
	@CircuitBreaker(name = "macronutrientBreaker", fallbackMethod = "getListObjectCB")
	@TimeLimiter(name = "macronutrientBreaker")
	@GetMapping("/macronutrient/all")
	@ResponseStatus(code = HttpStatus.OK)
	public CompletableFuture<List<Macronutrient>> getMacronutrients() {
		return CompletableFuture.supplyAsync(() -> macronutrientSvc.getMacronutrients());
	}

	// Get a macronutrient 
	@CircuitBreaker(name = "macronutrientBreaker", fallbackMethod = "getObjectCB")
	@TimeLimiter(name = "macronutrientBreaker")
	@GetMapping("/macronutrient/{idMacronutrient}")
	@ResponseStatus(code = HttpStatus.OK)
	public CompletableFuture<Macronutrient> getMacronutrient(@PathVariable Long idMacronutrient) {
		return CompletableFuture.supplyAsync(() -> macronutrientSvc.getMacronutrient(idMacronutrient));
	}

	// Add a macronutrient 
	@CircuitBreaker(name = "macronutrientBreaker", fallbackMethod = "getObjectCB")
	@TimeLimiter(name = "macronutrientBreaker")
	@PostMapping("/macronutrient/add")
	@ResponseStatus(code = HttpStatus.CREATED)
	public CompletableFuture<Macronutrient> addMacronutrient(@RequestBody Macronutrient macronutrient) {
		return CompletableFuture.supplyAsync(() -> macronutrientSvc.addMacronutrient(macronutrient));
	}

	// Update a macronutrient 
	@CircuitBreaker(name = "macronutrientBreaker", fallbackMethod = "getObjectCB")
	@TimeLimiter(name = "macronutrientBreaker")
	@PutMapping("/macronutrient/update/{idMacronutrient}")
	@ResponseStatus(code = HttpStatus.OK)
	public CompletableFuture<Macronutrient> updateMacronutrient(@RequestBody Macronutrient macronutrient,
			@PathVariable Long idMacronutrient) {
		return CompletableFuture.supplyAsync(() -> {
			Macronutrient currentMacronutrient = macronutrientSvc.getMacronutrient(idMacronutrient);
			currentMacronutrient.setKcal(macronutrient.getKcal());
			currentMacronutrient.setProtein(macronutrient.getProtein());
			currentMacronutrient.setCarbohydrate(macronutrient.getCarbohydrate());
			currentMacronutrient.setSugar(macronutrient.getSugar());
			currentMacronutrient.setAddedSugar(macronutrient.getAddedSugar());
			currentMacronutrient.setFat(macronutrient.getFat());
			currentMacronutrient.setSaturatedFat(macronutrient.getSaturatedFat());
			currentMacronutrient.setTrans(macronutrient.getTrans());
			currentMacronutrient.setFiber(macronutrient.getFiber());
			currentMacronutrient.setSodium(macronutrient.getSodium());
			currentMacronutrient.setPortion(macronutrient.getPortion());
			return macronutrientSvc.addMacronutrient(currentMacronutrient);
		});
	}

	// Delete a macronutrient 
	@CircuitBreaker(name = "macronutrientBreaker", fallbackMethod = "getVoidCB")
	@TimeLimiter(name = "macronutrientBreaker")
	@DeleteMapping("/macronutrient/{idMacronutrient}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public CompletableFuture<Void> deleteMacronutrient(@PathVariable Long idMacronutrient) {
		return CompletableFuture.runAsync(() -> macronutrientSvc.deleteMacronutrient(idMacronutrient));
	}

	// (CircuitBreaker) Get void circuit breaker
	public CompletableFuture<Void> getVoidCB(Throwable t) {
		return CompletableFuture.runAsync(() -> logger.error("Enabled macronutrient breaker" + t));
	}

	// (CircuitBreaker) Get object circuit breaker
	public CompletableFuture<Macronutrient> getObjectCB(Throwable t) {
		logger.error("Enabled macronutrient breaker" + t);
		return CompletableFuture.supplyAsync(() -> {
			Macronutrient ma = new Macronutrient();
			ma.setKcal(null);
			ma.setProtein(null);
			ma.setCarbohydrate(null);
			ma.setSugar(null);
			ma.setAddedSugar(null);
			ma.setFat(null);
			ma.setSaturatedFat(null);
			ma.setTrans(null);
			ma.setFiber(null);
			ma.setSodium(null);
			ma.setPortion(null);
			return ma;
		});
	}

	// (CircuitBreaker) Get list object circuit breaker
	public CompletableFuture<List<Macronutrient>> getListObjectCB(Throwable t) {
		logger.error("Enabled macronutrient breaker" + t);
		return CompletableFuture.supplyAsync(() -> {
			List<Macronutrient> list = new ArrayList<Macronutrient>();
			Macronutrient ma = new Macronutrient();
			ma.setKcal(null);
			ma.setProtein(null);
			ma.setCarbohydrate(null);
			ma.setSugar(null);
			ma.setAddedSugar(null);
			ma.setFat(null);
			ma.setSaturatedFat(null);
			ma.setTrans(null);
			ma.setFiber(null);
			ma.setSodium(null);
			ma.setPortion(null);
			list.add(ma);
			return list;
		});
	}
}
