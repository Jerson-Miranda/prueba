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
import com.evenjoin.diet_ms.entity.Mineral;
import com.evenjoin.diet_ms.services.MineralSvc;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;

@RefreshScope
@RestController
@RequestMapping("/diet")
public class MineralCtrl {

	private static final Logger logger = LoggerFactory.getLogger(MineralCtrl.class);

	@Autowired
	private MineralSvc mineralSvc;

	// Get all minerals
	@CircuitBreaker(name = "mineralBreaker", fallbackMethod = "getListObjectCB")
	@GetMapping("/mineral/all")
	@ResponseStatus(code = HttpStatus.OK)
	public CompletableFuture<List<Mineral>> getCategories() {
		return CompletableFuture.supplyAsync(() -> mineralSvc.getMinerals());
	}

	// Get a mineral
	@CircuitBreaker(name = "mineralBreaker", fallbackMethod = "getObjectCB")
	@TimeLimiter(name = "mineralBreaker")
	@GetMapping("/mineral/{idMineral}")
	@ResponseStatus(code = HttpStatus.OK)
	public CompletableFuture<Mineral> getMineral(@PathVariable Long idMineral) {
		return CompletableFuture.supplyAsync(() -> mineralSvc.getMineral(idMineral));
	}

	// Add a mineral
	@CircuitBreaker(name = "mineralBreaker", fallbackMethod = "getObjectCB")
	@TimeLimiter(name = "mineralBreaker")
	@PostMapping("/mineral/add")
	@ResponseStatus(code = HttpStatus.CREATED)
	public CompletableFuture<Mineral> addMineral(@RequestBody Mineral mineral) {
		return CompletableFuture.supplyAsync(() -> mineralSvc.addMineral(mineral));
	}

	// Update a mineral
	@CircuitBreaker(name = "mineralBreaker", fallbackMethod = "getObjectCB")
	@TimeLimiter(name = "mineralBreaker")
	@PutMapping("/mineral/update/{idMineral}")
	@ResponseStatus(code = HttpStatus.OK)
	public CompletableFuture<Mineral> updateMineral(@RequestBody Mineral mineral, @PathVariable Long idMineral) {
		return CompletableFuture.supplyAsync(() -> {
			Mineral currentMineral = mineralSvc.getMineral(idMineral);
			currentMineral.setCalcium(mineral.getCalcium());
			currentMineral.setPhosphorus(mineral.getPhosphorus());
			currentMineral.setSodium(mineral.getSodium());
			currentMineral.setChloride(mineral.getChloride());
			currentMineral.setMagnesium(mineral.getMagnesium());
			currentMineral.setSulfur(mineral.getSulfur());
			currentMineral.setIron(mineral.getIron());
			currentMineral.setZinc(mineral.getZinc());
			currentMineral.setCopper(mineral.getCopper());
			currentMineral.setManganese(mineral.getManganese());
			currentMineral.setIodine(mineral.getIodine());
			currentMineral.setSelenium(mineral.getSelenium());
			currentMineral.setMolybdenum(mineral.getMolybdenum());
			currentMineral.setCobalt(mineral.getCobalt());
			currentMineral.setFluoride(mineral.getFluoride());
			currentMineral.setChromium(mineral.getChromium());
			return mineralSvc.addMineral(currentMineral);
		});
	}

	// Delete a mineral
	@CircuitBreaker(name = "mineralBreaker", fallbackMethod = "getVoidCB")
	@TimeLimiter(name = "mineralBreaker")
	@DeleteMapping("/mineral/{idMineral}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public CompletableFuture<Void> deleteMineral(@PathVariable Long idMineral) {
		return CompletableFuture.runAsync(() -> mineralSvc.deleteMineral(idMineral));
	}
	
	// Get minerals by ingredient
	@CircuitBreaker(name = "mineralBreaker", fallbackMethod = "getObjectCB")
	@TimeLimiter(name = "mineralBreaker")
	@GetMapping("/mineral/ingredient/{barcode}")
	@ResponseStatus(code = HttpStatus.OK)
	public CompletableFuture<Mineral> getMineralsByIngredient(@PathVariable String barcode) {
		return CompletableFuture.supplyAsync(() -> mineralSvc.getMineralsByIngredient(barcode));
	}

	// (CircuitBreaker) Get void circuit breaker
	public CompletableFuture<Void> getVoidCB(Throwable t) {
		return CompletableFuture.runAsync(() -> logger.error("Error while deleting " + t));
	}

	// (CircuitBreaker) Get object circuit breaker
	public CompletableFuture<Mineral> getObjectCB(Throwable t) {
		logger.error("Enabled mineral breaker " + t);
		return CompletableFuture.supplyAsync(() -> {
			Mineral mineral = new Mineral();
			mineral.setIdMineral(null);
			mineral.setCalcium(null);
			mineral.setPhosphorus(null);
			mineral.setSodium(null);
			mineral.setChloride(null);
			mineral.setMagnesium(null);
			mineral.setSulfur(null);
			mineral.setIron(null);
			mineral.setZinc(null);
			mineral.setCopper(null);
			mineral.setManganese(null);
			mineral.setIodine(null);
			mineral.setSelenium(null);
			mineral.setMolybdenum(null);
			mineral.setCobalt(null);
			mineral.setFluoride(null);
			mineral.setChromium(null);
			return mineral;
		});
	}

	// (CircuitBreaker) Get list object circuit breaker
	public CompletableFuture<List<Mineral>> getListObjectCB(Throwable t) {
		logger.error("Enabled mineral breaker " + t);
		return CompletableFuture.supplyAsync(() -> {
			Mineral mineral = new Mineral();
			List<Mineral> list = new ArrayList<Mineral>();
			mineral.setIdMineral(null);
			mineral.setCalcium(null);
			mineral.setPhosphorus(null);
			mineral.setSodium(null);
			mineral.setChloride(null);
			mineral.setMagnesium(null);
			mineral.setSulfur(null);
			mineral.setIron(null);
			mineral.setZinc(null);
			mineral.setCopper(null);
			mineral.setManganese(null);
			mineral.setIodine(null);
			mineral.setSelenium(null);
			mineral.setMolybdenum(null);
			mineral.setCobalt(null);
			mineral.setFluoride(null);
			mineral.setChromium(null);
			list.add(mineral);
			return list;
		});
	}

}
