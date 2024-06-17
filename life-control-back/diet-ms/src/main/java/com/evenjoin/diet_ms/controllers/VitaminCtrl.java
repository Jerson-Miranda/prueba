package com.evenjoin.diet_ms.controllers;

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
	@GetMapping("/vitamin/ingredient/{idIngredient}")
	@ResponseStatus(code = HttpStatus.OK)
	public CompletableFuture<Vitamin> getVitaminsByIngredient(@PathVariable Long idIngredient) {
		return CompletableFuture.supplyAsync(() -> vitaminSvc.getVitaminsByIngredient(idIngredient));
	}

	// Get vitamins by recipe
	@CircuitBreaker(name = "vitaminBreaker", fallbackMethod = "getMapObjectCB")
	@TimeLimiter(name = "vitaminBreaker")
	@GetMapping("/vitamin/recipe/{idRecipe}")
	@ResponseStatus(code = HttpStatus.OK)
	public CompletableFuture<Map<String, Object>> getVitaminsByRecipe(@PathVariable Long idRecipe) {
		return CompletableFuture.supplyAsync(() -> {
			Object response = vitaminSvc.getVitaminsByRecipe(idRecipe);
			Object[] res = (Object[]) response;
			Map<String, Object> jsonObject = new HashMap<String, Object>();
			jsonObject.put("vitaminA", res[0]);
			jsonObject.put("vitaminB1", res[1]);
			jsonObject.put("vitaminB2", res[2]);
			jsonObject.put("vitaminB3", res[3]);
			jsonObject.put("vitaminB4", res[4]);
			jsonObject.put("vitaminB5", res[5]);
			jsonObject.put("vitaminB6", res[6]);
			jsonObject.put("vitaminB7", res[7]);
			jsonObject.put("vitaminB8", res[8]);
			jsonObject.put("vitaminB9", res[9]);
			jsonObject.put("vitaminB12", res[10]);
			jsonObject.put("vitaminC", res[11]);
			jsonObject.put("vitaminD2", res[12]);
			jsonObject.put("vitaminD3", res[13]);
			jsonObject.put("vitaminE", res[14]);
			jsonObject.put("vitaminK1", res[15]);
			jsonObject.put("vitaminK2", res[16]);
			return jsonObject;
		});
	}

	// Get vitamins by diet
	@CircuitBreaker(name = "vitaminBreaker", fallbackMethod = "getMapObjectCB")
	@TimeLimiter(name = "vitaminBreaker")
	@GetMapping("/vitamin/diet/{idDiet}")
	@ResponseStatus(code = HttpStatus.OK)
	public CompletableFuture<Map<String, Object>> getVitaminsByDiet(@PathVariable Long idDiet) {
		return CompletableFuture.supplyAsync(() -> {
			Object response = vitaminSvc.getVitaminsByDiet(idDiet);
			Object[] res = (Object[]) response;
			Map<String, Object> jsonObject = new HashMap<String, Object>();
			jsonObject.put("vitaminA", res[0]);
			jsonObject.put("vitaminB1", res[1]);
			jsonObject.put("vitaminB2", res[2]);
			jsonObject.put("vitaminB3", res[3]);
			jsonObject.put("vitaminB4", res[4]);
			jsonObject.put("vitaminB5", res[5]);
			jsonObject.put("vitaminB6", res[6]);
			jsonObject.put("vitaminB7", res[7]);
			jsonObject.put("vitaminB8", res[8]);
			jsonObject.put("vitaminB9", res[9]);
			jsonObject.put("vitaminB12", res[10]);
			jsonObject.put("vitaminC", res[11]);
			jsonObject.put("vitaminD2", res[12]);
			jsonObject.put("vitaminD3", res[13]);
			jsonObject.put("vitaminE", res[14]);
			jsonObject.put("vitaminK1", res[15]);
			jsonObject.put("vitaminK2", res[16]);
			return jsonObject;
		});
	}

	// Get vitamins by diet between dates
	@CircuitBreaker(name = "vitaminBreaker", fallbackMethod = "getMapObjectCB")
	@TimeLimiter(name = "vitaminBreaker")
	@GetMapping("/vitamin/diet/{startDate}/{endDate}")
	@ResponseStatus(code = HttpStatus.OK)
	public CompletableFuture<List<Map<String, Object>>> getVitaminsByDietRange(@PathVariable String startDate,
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
			List<Object> response = vitaminSvc.getVitaminsByDietRange(startDate1, endDate1);
			List<Map<String, Object>> json = new ArrayList<Map<String, Object>>();
			for (Object res : response) {
				Object[] row = (Object[]) res;
				Map<String, Object> jsonObject = new HashMap<String, Object>();
				jsonObject.put("idDiet", row[0]);
				jsonObject.put("date", row[1]);
				jsonObject.put("vitaminA", row[2]);
				jsonObject.put("vitaminB1", row[3]);
				jsonObject.put("vitaminB2", row[4]);
				jsonObject.put("vitaminB3", row[5]);
				jsonObject.put("vitaminB4", row[6]);
				jsonObject.put("vitaminB5", row[7]);
				jsonObject.put("vitaminB6", row[8]);
				jsonObject.put("vitaminB7", row[9]);
				jsonObject.put("vitaminB8", row[10]);
				jsonObject.put("vitaminB9", row[11]);
				jsonObject.put("vitaminB12", row[12]);
				jsonObject.put("vitaminC", row[13]);
				jsonObject.put("vitaminD2", row[14]);
				jsonObject.put("vitaminD3", row[15]);
				jsonObject.put("vitaminE", row[16]);
				jsonObject.put("vitaminK1", row[17]);
				jsonObject.put("vitaminK2", row[18]);
				json.add(jsonObject);
			}
			return json;
		});
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

	// (CircuitBreaker) Get map object circuit breaker
	public CompletableFuture<Map<String, Object>> getMapObjectCB(Throwable t) {
		logger.error("Enabled vitamin breaker " + t);
		return CompletableFuture.supplyAsync(() -> {
			Map<String, Object> jsonObject = new HashMap<String, Object>();
			jsonObject.put("vitaminA", null);
			jsonObject.put("vitaminB1", null);
			jsonObject.put("vitaminB2", null);
			jsonObject.put("vitaminB3", null);
			jsonObject.put("vitaminB4", null);
			jsonObject.put("vitaminB5", null);
			jsonObject.put("vitaminB6", null);
			jsonObject.put("vitaminB7", null);
			jsonObject.put("vitaminB8", null);
			jsonObject.put("vitaminB9", null);
			jsonObject.put("vitaminB12", null);
			jsonObject.put("vitaminC", null);
			jsonObject.put("vitaminD2", null);
			jsonObject.put("vitaminD3", null);
			jsonObject.put("vitaminE", null);
			jsonObject.put("vitaminK1", null);
			jsonObject.put("vitaminK2", null);
			return jsonObject;
		});
	}

}
