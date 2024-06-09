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
import com.evenjoin.diet_ms.entity.NutritionalFact;
import com.evenjoin.diet_ms.services.NutritionalFactSvc;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;

@RefreshScope
@RestController
@RequestMapping("/diet")
public class NutritionalFactCtrl {

	private static final Logger logger = LoggerFactory.getLogger(NutritionalFactCtrl.class);

	@Autowired
	private NutritionalFactSvc nutritionalFactSvc;

	// Get all nutritional facts
	@CircuitBreaker(name = "nutritionalFactBreaker", fallbackMethod = "getListObjectCB")
	@TimeLimiter(name = "nutritionalFactBreaker")
	@GetMapping("/nutritional-fact/all")
	@ResponseStatus(code = HttpStatus.OK)
	public CompletableFuture<List<NutritionalFact>> getNutritionalFacts() {
		return CompletableFuture.supplyAsync(() -> nutritionalFactSvc.getNutritionalFacts());
	}

	// Get a nutritional fact
	@CircuitBreaker(name = "nutritionalFactBreaker", fallbackMethod = "getObjectCB")
	@TimeLimiter(name = "nutritionalFactBreaker")
	@GetMapping("/nutritional-fact/{idNutritionalFact}")
	@ResponseStatus(code = HttpStatus.OK)
	public CompletableFuture<NutritionalFact> getNutritionalFact(@PathVariable Long idNutritionalFact) {
		return CompletableFuture.supplyAsync(() -> nutritionalFactSvc.getNutritionalFact(idNutritionalFact));
	}

	// Add a nutritional fact
	@CircuitBreaker(name = "nutritionalFactBreaker", fallbackMethod = "getObjectCB")
	@TimeLimiter(name = "nutritionalFactBreaker")
	@PostMapping("/nutritional-fact/add")
	@ResponseStatus(code = HttpStatus.CREATED)
	public CompletableFuture<NutritionalFact> addNutritionalFact(@RequestBody NutritionalFact nutritionalFact) {
		return CompletableFuture.supplyAsync(() -> nutritionalFactSvc.addNutritionalFact(nutritionalFact));
	}

	// Update a nutritional fact
	@CircuitBreaker(name = "nutritionalFactBreaker", fallbackMethod = "getObjectCB")
	@TimeLimiter(name = "nutritionalFactBreaker")
	@PutMapping("/nutritional-fact/update/{idNutritionalFact}")
	@ResponseStatus(code = HttpStatus.OK)
	public CompletableFuture<NutritionalFact> updateNutritionalFact(@RequestBody NutritionalFact nutritionalFact,
			@PathVariable Long idNutritionalFact) {
		return CompletableFuture.supplyAsync(() -> {
			NutritionalFact currentNutritionalFact = nutritionalFactSvc.getNutritionalFact(idNutritionalFact);
			currentNutritionalFact.setKcal(nutritionalFact.getKcal());
			currentNutritionalFact.setProtein(nutritionalFact.getProtein());
			currentNutritionalFact.setCarbohydrate(nutritionalFact.getCarbohydrate());
			currentNutritionalFact.setSugar(nutritionalFact.getSugar());
			currentNutritionalFact.setAddedSugar(nutritionalFact.getAddedSugar());
			currentNutritionalFact.setFat(nutritionalFact.getFat());
			currentNutritionalFact.setSaturatedFat(nutritionalFact.getSaturatedFat());
			currentNutritionalFact.setTrans(nutritionalFact.getTrans());
			currentNutritionalFact.setFiber(nutritionalFact.getFiber());
			currentNutritionalFact.setSodium(nutritionalFact.getSodium());
			currentNutritionalFact.setPortion(nutritionalFact.getPortion());
			return nutritionalFactSvc.addNutritionalFact(currentNutritionalFact);
		});
	}

	// Delete a nutritional fact
	@CircuitBreaker(name = "nutritionalFactBreaker", fallbackMethod = "getVoidCB")
	@TimeLimiter(name = "nutritionalFactBreaker")
	@DeleteMapping("/nutritional-fact/{idNutritionalFact}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public CompletableFuture<Void> deleteNutritionalFact(@PathVariable Long idNutritionalFact) {
		return CompletableFuture.runAsync(() -> nutritionalFactSvc.deleteNutritionalFact(idNutritionalFact));
	}

	// (CircuitBreaker) Get void circuit breaker
	public CompletableFuture<Void> getVoidCB(Throwable t) {
		return CompletableFuture.runAsync(() -> logger.error("Enabled nutritional fact breaker" + t));
	}

	// (CircuitBreaker) Get object circuit breaker
	public CompletableFuture<NutritionalFact> getObjectCB(Throwable t) {
		logger.error("Enabled nutritional fact breaker" + t);
		return CompletableFuture.supplyAsync(() -> {
			NutritionalFact nf = new NutritionalFact();
			nf.setKcal(null);
			nf.setProtein(null);
			nf.setCarbohydrate(null);
			nf.setSugar(null);
			nf.setAddedSugar(null);
			nf.setFat(null);
			nf.setSaturatedFat(null);
			nf.setTrans(null);
			nf.setFiber(null);
			nf.setSodium(null);
			nf.setPortion(null);
			return nf;
		});
	}

	// (CircuitBreaker) Get list object circuit breaker
	public CompletableFuture<List<NutritionalFact>> getListObjectCB(Throwable t) {
		logger.error("Enabled nutritional fact breaker" + t);
		return CompletableFuture.supplyAsync(() -> {
			List<NutritionalFact> list = new ArrayList<NutritionalFact>();
			NutritionalFact nf = new NutritionalFact();
			nf.setKcal(null);
			nf.setProtein(null);
			nf.setCarbohydrate(null);
			nf.setSugar(null);
			nf.setAddedSugar(null);
			nf.setFat(null);
			nf.setSaturatedFat(null);
			nf.setTrans(null);
			nf.setFiber(null);
			nf.setSodium(null);
			nf.setPortion(null);
			list.add(nf);
			return list;
		});
	}
}
