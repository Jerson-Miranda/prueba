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
import com.evenjoin.diet_ms.entity.ScheduleDiet;
import com.evenjoin.diet_ms.services.ScheduleDietSvc;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;

@RefreshScope
@RestController
@RequestMapping("/diet")
public class ScheduleDietCtrl {
    
    private static final Logger logger = LoggerFactory.getLogger(ScheduleDietCtrl.class);

	@Autowired
	private ScheduleDietSvc scheduleDietSvc;

	// Get all schedule diets
	@CircuitBreaker(name = "scheduleDietBreaker", fallbackMethod = "getListObjectCB")
    @TimeLimiter(name = "scheduleDietBreaker")
	@GetMapping("/schedule-diet/all")
	@ResponseStatus(code = HttpStatus.OK)
	public CompletableFuture<List<ScheduleDiet>> getSchedules() {
		return CompletableFuture.supplyAsync(() -> scheduleDietSvc.getScheduleDiets());
	}

	// Get a schedule diet
	@CircuitBreaker(name = "scheduleDietBreaker", fallbackMethod = "getObjectCB")
	@TimeLimiter(name = "scheduleDietBreaker")
	@GetMapping("/schedule-diet/{idScheduleDiet}")
	@ResponseStatus(code = HttpStatus.OK)
	public CompletableFuture<ScheduleDiet> getScheduleDiet(@PathVariable Long idScheduleDiet) {
		return CompletableFuture.supplyAsync(() -> scheduleDietSvc.getScheduleDiet(idScheduleDiet));
	}

	// Add a schedule diet
	@CircuitBreaker(name = "scheduleDietBreaker", fallbackMethod = "getObjectCB")
	@TimeLimiter(name = "scheduleDietBreaker")
	@PostMapping("/schedule-diet/add")
	@ResponseStatus(code = HttpStatus.CREATED)
	public CompletableFuture<ScheduleDiet> addScheduleDiet(@RequestBody ScheduleDiet scheduleDiet) {
		return CompletableFuture.supplyAsync(() -> scheduleDietSvc.addScheduleDiet(scheduleDiet));
	}

	// Update a schedule diet
	@CircuitBreaker(name = "scheduleDietBreaker", fallbackMethod = "getObjectCB")
	@TimeLimiter(name = "scheduleDietBreaker")
	@PutMapping("/schedule-diet/update/{idScheduleDiet}")
	@ResponseStatus(code = HttpStatus.OK)
	public CompletableFuture<ScheduleDiet> updateScheduleDiet(@RequestBody ScheduleDiet scheduleDiet, @PathVariable Long idScheduleDiet) {
		return CompletableFuture.supplyAsync(() -> {
			ScheduleDiet currentSchedule = scheduleDietSvc.getScheduleDiet(idScheduleDiet);
			currentSchedule.setTime(scheduleDiet.getTime());
			currentSchedule.setIsChecked(scheduleDiet.getIsChecked());
            currentSchedule.setDiet(scheduleDiet.getDiet());
            currentSchedule.setRecipe(scheduleDiet.getRecipe());
			return scheduleDietSvc.addScheduleDiet(currentSchedule);
		});
	}

	// Delete a schedule diet
	@CircuitBreaker(name = "scheduleDietBreaker", fallbackMethod = "getVoidCB")
	@TimeLimiter(name = "scheduleDietBreaker")
	@DeleteMapping("/schedule-diet/{idScheduleDiet}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public CompletableFuture<Void> deleteScheduleDiet(@PathVariable Long idScheduleDiet) {
		return CompletableFuture.runAsync(() -> scheduleDietSvc.deleteScheduleDiet(idScheduleDiet));
	}

    // (CircuitBreaker) Get void circuit breaker
	public CompletableFuture<Void> getVoidCB(Throwable t) {
		return CompletableFuture.runAsync(() -> logger.error("Error while deleting " + t));
	}

	// (CircuitBreaker) Get object circuit breaker
	public CompletableFuture<ScheduleDiet> getObjectCB(Throwable t) {
		logger.error("Enabled schedule diet breaker " + t);
		return CompletableFuture.supplyAsync(() -> {
			ScheduleDiet scheduleDiet = new ScheduleDiet();
			scheduleDiet.setIdScheduleDiet(null);
			scheduleDiet.setTime(null);
			scheduleDiet.setIsChecked(null);
            scheduleDiet.setDiet(null);
            scheduleDiet.setRecipe(null);
			return scheduleDiet;
		});
	}

	// (CircuitBreaker) Get list object circuit breaker
	public CompletableFuture<List<ScheduleDiet>> getListObjectCB(Throwable t) {
		logger.error("Enabled schedule diet breaker " + t);
		return CompletableFuture.supplyAsync(() -> {
			ScheduleDiet scheduleDiet = new ScheduleDiet();
			List<ScheduleDiet> list = new ArrayList<ScheduleDiet>();
			scheduleDiet.setIdScheduleDiet(null);
			scheduleDiet.setTime(null);
			scheduleDiet.setIsChecked(null);
            scheduleDiet.setDiet(null);
            scheduleDiet.setRecipe(null);
			list.add(scheduleDiet);
			return list;
		});
	}
}
