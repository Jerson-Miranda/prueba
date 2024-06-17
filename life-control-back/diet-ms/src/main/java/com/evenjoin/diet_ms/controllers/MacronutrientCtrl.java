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

	// Get all macronutrients
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

	// Get macronutrients by ingredient
	@CircuitBreaker(name = "macronutrientBreaker", fallbackMethod = "getObjectCB")
	@TimeLimiter(name = "macronutrientBreaker")
	@GetMapping("/macronutrient/ingredient/{idIngredient}")
	@ResponseStatus(code = HttpStatus.OK)
	public CompletableFuture<Macronutrient> getMacronutrientsByIngredient(@PathVariable Long idIngredient) {
		return CompletableFuture.supplyAsync(() -> macronutrientSvc.getMacronutrientsByIngredient(idIngredient));
	}

	// Get macronutrients by recipe
	@CircuitBreaker(name = "macronutrientBreaker", fallbackMethod = "getMapObjectCB")
	@TimeLimiter(name = "macronutrientBreaker")
	@GetMapping("/macronutrient/recipe/{idRecipe}")
	@ResponseStatus(code = HttpStatus.OK)
	public CompletableFuture<Map<String, Object>> getMacronutrientsByRecipe(@PathVariable Long idRecipe) {
		return CompletableFuture.supplyAsync(() -> {
			Object response = macronutrientSvc.getMacronutrientsByRecipe(idRecipe);
			Object[] res = (Object[]) response;
			Map<String, Object> jsonObject = new HashMap<String, Object>();
			jsonObject.put("kcal", res[0]);
			jsonObject.put("protein", res[1]);
			jsonObject.put("carbohydrate", res[2]);
			jsonObject.put("sugar", res[3]);
			jsonObject.put("addedSugar", res[4]);
			jsonObject.put("fat", res[5]);
			jsonObject.put("saturatedFat", res[6]);
			jsonObject.put("trans", res[7]);
			jsonObject.put("fiber", res[8]);
			jsonObject.put("sodium", res[9]);
			return jsonObject;
		});
	}

	// Get macronutrients by diet
	@CircuitBreaker(name = "macronutrientBreaker", fallbackMethod = "getMapObjectCB")
	@TimeLimiter(name = "macronutrientBreaker")
	@GetMapping("/macronutrient/diet/{idDiet}")
	@ResponseStatus(code = HttpStatus.OK)
	public CompletableFuture<Map<String, Object>> getMacronutrientsByDiet(@PathVariable Long idDiet) {
		return CompletableFuture.supplyAsync(() -> {
			Object response = macronutrientSvc.getMacronutrientsByDiet(idDiet);
			Object[] res = (Object[]) response;
			Map<String, Object> jsonObject = new HashMap<String, Object>();
			jsonObject.put("kcal", res[0]);
			jsonObject.put("protein", res[1]);
			jsonObject.put("carbohydrate", res[2]);
			jsonObject.put("sugar", res[3]);
			jsonObject.put("addedSugar", res[4]);
			jsonObject.put("fat", res[5]);
			jsonObject.put("saturatedFat", res[6]);
			jsonObject.put("trans", res[7]);
			jsonObject.put("fiber", res[8]);
			jsonObject.put("sodium", res[9]);
			return jsonObject;
		});
	}

	// Get macronutrients by diet between dates
	@CircuitBreaker(name = "macronutrientBreaker", fallbackMethod = "getMapObjectCB")
	@TimeLimiter(name = "macronutrientBreaker")
	@GetMapping("/macronutrient/diet/{startDate}/{endDate}")
	@ResponseStatus(code = HttpStatus.OK)
	public CompletableFuture<List<Map<String, Object>>> getMacronutrientsByDietRange(@PathVariable String startDate,
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
			List<Object> response = macronutrientSvc.getMacronutrientsByDietRange(startDate1, endDate1);
			List<Map<String, Object>> json = new ArrayList<Map<String, Object>>();
			for (Object res : response) {
				Object[] row = (Object[]) res;
				Map<String, Object> jsonObject = new HashMap<String, Object>();
				jsonObject.put("idDiet", row[0]);
				jsonObject.put("date", row[1]);
				jsonObject.put("kcal", row[2]);
				jsonObject.put("protein", row[3]);
				jsonObject.put("carbohydrate", row[4]);
				jsonObject.put("sugar", row[5]);
				jsonObject.put("addedSugar", row[6]);
				jsonObject.put("fat", row[7]);
				jsonObject.put("saturatedFat", row[8]);
				jsonObject.put("trans", row[9]);
				jsonObject.put("fiber", row[10]);
				jsonObject.put("sodium", row[11]);
				json.add(jsonObject);
			}
			return json;
		});
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
			ma.setIdMacronutrient(null);
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
			ma.setIdMacronutrient(null);
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

	// (CircuitBreaker) Get map object circuit breaker
	public CompletableFuture<Map<String, Object>> getMapObjectCB(Throwable t) {
		logger.error("Enabled macronutrient breaker " + t);
		return CompletableFuture.supplyAsync(() -> {
			Map<String, Object> jsonObject = new HashMap<String, Object>();
			jsonObject.put("kcal", null);
			jsonObject.put("protein", null);
			jsonObject.put("carbohydrate", null);
			jsonObject.put("sugar", null);
			jsonObject.put("addedSugar", null);
			jsonObject.put("fat", null);
			jsonObject.put("saturatedFat", null);
			jsonObject.put("trans", null);
			jsonObject.put("fiber", null);
			jsonObject.put("sodium", null);
			return jsonObject;
		});
	}

}
