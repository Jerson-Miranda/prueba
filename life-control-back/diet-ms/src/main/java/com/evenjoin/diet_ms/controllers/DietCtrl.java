package com.evenjoin.diet_ms.controllers;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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

	// Get total price by diet
	@CircuitBreaker(name = "dietBreaker", fallbackMethod = "getMapDecimalCB")
	@TimeLimiter(name = "dietBreaker")
	@GetMapping("/diet/price/{idDiet}")
	@ResponseStatus(code = HttpStatus.OK)
	public CompletableFuture<Map<String, BigDecimal>> getPriceByDiet(@PathVariable Long idDiet) {
		return CompletableFuture.supplyAsync(() -> {
			BigDecimal response = dietSvc.getPriceByDiet(idDiet);
			Map<String, BigDecimal> jsonObject = new HashMap<String, BigDecimal>();
			jsonObject.put("price", response);
			return jsonObject;
		});
	}

	// Get total price by diet between dates
	@CircuitBreaker(name = "dietBreaker", fallbackMethod = "getMapDecimalCB")
	@TimeLimiter(name = "dietBreaker")
	@GetMapping("/diet/price/{startDate}/{endDate}")
	@ResponseStatus(code = HttpStatus.OK)
	public CompletableFuture<Map<String, BigDecimal>> getPriceByDietRange(@PathVariable String startDate,
			@PathVariable String endDate) {
		return CompletableFuture.supplyAsync(() -> {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			Date startDate1;
			Date endDate1;
			try {
				startDate1 = formatter.parse(startDate);
				endDate1 = formatter.parse(endDate);
			} catch (ParseException e) {
				throw new RuntimeException("Invalid date: " + e.getMessage());
			}
			BigDecimal response = dietSvc.getPriceByDietRange(startDate1, endDate1);
			Map<String, BigDecimal> jsonObject = new HashMap<String, BigDecimal>();
			jsonObject.put("price", response);
			return jsonObject;
		});
	}

	// Get prices by diet between dates
	@CircuitBreaker(name = "dietBreaker", fallbackMethod = "getListMapObjectCB")
	@TimeLimiter(name = "dietBreaker")
	@GetMapping("/diet/prices/{startDate}/{endDate}")
	@ResponseStatus(code = HttpStatus.OK)
	public CompletableFuture<List<Map<String, Object>>> getPricesByDietRange(@PathVariable String startDate,
			@PathVariable String endDate) {
		return CompletableFuture.supplyAsync(() -> {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			Date startDate1;
			Date endDate1;
			try {
				startDate1 = formatter.parse(startDate);
				endDate1 = formatter.parse(endDate);
			} catch (ParseException e) {
				throw new RuntimeException("Invalid date: " + e.getMessage());
			}
			List<Object> response = dietSvc.getPricesByDietRange(startDate1, endDate1);
			List<Map<String, Object>> json = new ArrayList<Map<String, Object>>();
			for (Object res : response) {
				Object[] row = (Object[]) res;
				Map<String, Object> jsonObject = new HashMap<String, Object>();
				jsonObject.put("idRecipe", row[0]);
				jsonObject.put("price", row[1]);
				json.add(jsonObject);
			}
			return json;
		});
	}

	// Get total time by diet
	@CircuitBreaker(name = "dietBreaker", fallbackMethod = "getMapIntegerCB")
	@TimeLimiter(name = "dietBreaker")
	@GetMapping("/diet/time/{idDiet}")
	@ResponseStatus(code = HttpStatus.OK)
	public CompletableFuture<Map<String, Integer>> getTimeByDiet(@PathVariable Long idDiet) {
		return CompletableFuture.supplyAsync(() -> {
			Integer response = dietSvc.getTimeByDiet(idDiet);
			Map<String, Integer> jsonObject = new HashMap<String, Integer>();
			jsonObject.put("timeMinute", response);
			return jsonObject;
		});
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
			diet.setIdDiet(null);
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
			diet.setIdDiet(null);
			diet.setDate(null);
			list.add(diet);
			return list;
		});
	}

	// (CircuitBreaker) Get list map object circuit breaker
	public CompletableFuture<List<Map<String, Object>>> getListMapObjectCB(Throwable t) {
		logger.error("Enabled diet breaker" + t);
		return CompletableFuture.supplyAsync(() -> {
			List<Map<String, Object>> json = new ArrayList<Map<String, Object>>();
			Map<String, Object> jsonObject = new HashMap<String, Object>();
			jsonObject.put("idRecipe", null);
			jsonObject.put("price", null);
			json.add(jsonObject);
			return json;
		});
	}

	// (CircuitBreaker) Get map decimal circuit breaker
	public CompletableFuture<Map<String, BigDecimal>> getMapDecimalCB(Throwable t) {
		logger.error("Enabled diet breaker" + t);
		return CompletableFuture.supplyAsync(() -> {
			Map<String, BigDecimal> jsonObject = new HashMap<String, BigDecimal>();
			jsonObject.put("price", null);
			return jsonObject;
		});
	}

	// (CircuitBreaker) Get map integer circuit breaker
	public CompletableFuture<Map<String, Integer>> getMapIntegerCB(Throwable t) {
		logger.error("Enabled diet breaker" + t);
		return CompletableFuture.supplyAsync(() -> {
			Map<String, Integer> jsonObject = new HashMap<String, Integer>();
			jsonObject.put("timeMinute", null);
			return jsonObject;
		});
	}

}
