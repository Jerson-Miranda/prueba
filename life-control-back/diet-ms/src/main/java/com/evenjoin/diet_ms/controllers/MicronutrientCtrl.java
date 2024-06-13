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
import com.evenjoin.diet_ms.entity.Micronutrient;
import com.evenjoin.diet_ms.services.MicronutrientSvc;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;

@RefreshScope
@RestController
@RequestMapping("/diet")
public class MicronutrientCtrl {

	private static final Logger logger = LoggerFactory.getLogger(MicronutrientCtrl.class);

	@Autowired
	private MicronutrientSvc micronutrientSvc;

	// Get all micronutrients
	@CircuitBreaker(name = "micronutrientBreaker", fallbackMethod = "getListObjectCB")
	@TimeLimiter(name = "micronutrientBreaker")
	@GetMapping("/micronutrient/all")
	@ResponseStatus(code = HttpStatus.OK)
	public CompletableFuture<List<Micronutrient>> getMicronutrients() {
		return CompletableFuture.supplyAsync(() -> micronutrientSvc.getMicronutrients());
	}

	// Get a micronutrient 
	@CircuitBreaker(name = "micronutrientBreaker", fallbackMethod = "getObjectCB")
	@TimeLimiter(name = "micronutrientBreaker")
	@GetMapping("/micronutrient/{idMicronutrient}")
	@ResponseStatus(code = HttpStatus.OK)
	public CompletableFuture<Micronutrient> getMicronutrient(@PathVariable Long idMicronutrient) {
		return CompletableFuture.supplyAsync(() -> micronutrientSvc.getMicronutrient(idMicronutrient));
	}

	// Add a micronutrient 
	@CircuitBreaker(name = "micronutrientBreaker", fallbackMethod = "getObjectCB")
	@TimeLimiter(name = "micronutrientBreaker")
	@PostMapping("/micronutrient/add")
	@ResponseStatus(code = HttpStatus.CREATED)
	public CompletableFuture<Micronutrient> addMicronutrient(@RequestBody Micronutrient micronutrient) {
		return CompletableFuture.supplyAsync(() -> micronutrientSvc.addMicronutrient(micronutrient));
	}

	// Update a micronutrient 
	@CircuitBreaker(name = "micronutrientBreaker", fallbackMethod = "getObjectCB")
	@TimeLimiter(name = "micronutrientBreaker")
	@PutMapping("/micronutrient/update/{idMicronutrient}")
	@ResponseStatus(code = HttpStatus.OK)
	public CompletableFuture<Micronutrient> updateMicronutrient(@RequestBody Micronutrient micronutrient,
			@PathVariable Long idMicronutrient) {
		return CompletableFuture.supplyAsync(() -> {
			Micronutrient currentMicronutrient = micronutrientSvc.getMicronutrient(idMicronutrient);
			currentMicronutrient.setVitamin(micronutrient.getVitamin());
			currentMicronutrient.setMineral(micronutrient.getMineral());
			return micronutrientSvc.addMicronutrient(currentMicronutrient);
		});
	}

	// Delete a micronutrient 
	@CircuitBreaker(name = "micronutrientBreaker", fallbackMethod = "getVoidCB")
	@TimeLimiter(name = "micronutrientBreaker")
	@DeleteMapping("/micronutrient/{idMicronutrient}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public CompletableFuture<Void> deleteMicronutrient(@PathVariable Long idMicronutrient) {
		return CompletableFuture.runAsync(() -> micronutrientSvc.deleteMicronutrient(idMicronutrient));
	}
	
	// Get micronutrients by ingredient
	@CircuitBreaker(name = "micronutrientBreaker", fallbackMethod = "getObjectCB")
	@TimeLimiter(name = "micronutrientBreaker")
	@GetMapping("/micronutrient/ingredient/{barcode}")
	@ResponseStatus(code = HttpStatus.OK)
	public CompletableFuture<Micronutrient> getMicronutrientsByIngredient(@PathVariable String barcode) {
		return CompletableFuture.supplyAsync(() -> micronutrientSvc.getMicronutrientsByIngredient(barcode));
	}

	// (CircuitBreaker) Get void circuit breaker
	public CompletableFuture<Void> getVoidCB(Throwable t) {
		return CompletableFuture.runAsync(() -> logger.error("Enabled micronutrient breaker" + t));
	}

	// (CircuitBreaker) Get object circuit breaker
	public CompletableFuture<Micronutrient> getObjectCB(Throwable t) {
		logger.error("Enabled micronutrient breaker" + t);
		return CompletableFuture.supplyAsync(() -> {
			Micronutrient micronutrient = new Micronutrient();
			micronutrient.setIdMicronutrient(null);
			micronutrient.setVitamin(null);
			micronutrient.setMineral(null);
			return micronutrient;
		});
	}

	// (CircuitBreaker) Get list object circuit breaker
	public CompletableFuture<List<Micronutrient>> getListObjectCB(Throwable t) {
		logger.error("Enabled micronutrient breaker" + t);
		return CompletableFuture.supplyAsync(() -> {
			List<Micronutrient> list = new ArrayList<Micronutrient>();
			Micronutrient micronutrient = new Micronutrient();
			micronutrient.setIdMicronutrient(null);
			micronutrient.setVitamin(null);
			micronutrient.setMineral(null);
			list.add(micronutrient);
			return list;
		});
	}
}
