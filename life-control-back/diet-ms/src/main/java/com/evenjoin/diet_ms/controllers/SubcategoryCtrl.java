package com.evenjoin.diet_ms.controllers;

import java.util.ArrayList;
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
import com.evenjoin.diet_ms.entity.Subcategory;
import com.evenjoin.diet_ms.services.SubcategorySvc;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;

@RefreshScope
@RestController
@RequestMapping("/diet")
public class SubcategoryCtrl {

	private static final Logger logger = LoggerFactory.getLogger(Subcategory.class);

	@Autowired
	private SubcategorySvc subcategorySvc;

	// Get all subcategories
	@CircuitBreaker(name = "subcategoryBreaker", fallbackMethod = "getListObjectCB")
	@TimeLimiter(name = "subcategoryBreaker")
	@GetMapping("/subcategory/all")
	@ResponseStatus(code = HttpStatus.OK)
	public CompletableFuture<List<Subcategory>> getSubcategories() {
		return CompletableFuture.supplyAsync(() -> subcategorySvc.getSubcategories());
	}

	// Get a subcategory
	@CircuitBreaker(name = "subcategoryBreaker", fallbackMethod = "getObjectCB")
	@TimeLimiter(name = "subcategoryBreaker")
	@GetMapping("/subcategory/{idSubcategory}")
	@ResponseStatus(code = HttpStatus.OK)
	public CompletableFuture<Subcategory> getSubcategory(@PathVariable Long idSubcategory) {
		return CompletableFuture.supplyAsync(() -> subcategorySvc.getSubcategory(idSubcategory));
	}

	// Add a subcategory
	@CircuitBreaker(name = "subcategoryBreaker", fallbackMethod = "getObjectCB")
	@TimeLimiter(name = "subcategoryBreaker")
	@PostMapping("/subcategory/add")
	@ResponseStatus(code = HttpStatus.CREATED)
	public CompletableFuture<Subcategory> addSubcategory(@RequestBody Subcategory subcategory) {
		return CompletableFuture.supplyAsync(() -> subcategorySvc.addSubcategory(subcategory));
	}

	// Update a subcategory
	@CircuitBreaker(name = "subcategoryBreaker", fallbackMethod = "getObjectCB")
	@TimeLimiter(name = "subcategoryBreaker")
	@PutMapping("/subcategory/update/{idSubcategory}")
	@ResponseStatus(code = HttpStatus.OK)
	public CompletableFuture<Subcategory> updateSubcategory(@RequestBody Subcategory subcategory,
			@PathVariable Long idSubcategory) {
		return CompletableFuture.supplyAsync(() -> {
			Subcategory currentSubcategory = subcategorySvc.getSubcategory(idSubcategory);
			currentSubcategory.setName(subcategory.getName());
			currentSubcategory.setCategory(subcategory.getCategory());
			return subcategorySvc.addSubcategory(currentSubcategory);
		});
	}

	// Delete a subcategory
	@CircuitBreaker(name = "subcategoryBreaker", fallbackMethod = "getVoidCB")
	@TimeLimiter(name = "subcategoryBreaker")
	@DeleteMapping("/subcategory/{idSubcategory}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public CompletableFuture<Void> deleteSubcategory(@PathVariable Long idSubcategory) {
		return CompletableFuture.runAsync(() -> subcategorySvc.deleteSubcategory(idSubcategory));
	}
	
	// Count subcategories
	@CircuitBreaker(name = "subcategoryBreaker", fallbackMethod = "getMapIntegerCB")
	@TimeLimiter(name = "subcategoryBreaker")
	@GetMapping("/subcategory/count")
	@ResponseStatus(code = HttpStatus.OK)
	public CompletableFuture<Map<String, Integer>> countSubcategories(){
		return CompletableFuture.supplyAsync(()-> {
			Map<String, Integer> jsonObject = new HashMap<String, Integer>();
			jsonObject.put("count", subcategorySvc.countSubcategories());
			return jsonObject;
		});
	}

	// Get subcategories by recipe book
	@CircuitBreaker(name = "subcategoryBreaker", fallbackMethod = "getListObjectCB")
	@TimeLimiter(name = "subcategoryBreaker")
	@GetMapping("/subcategory/recipe-book/{idRecipeBook}")
	@ResponseStatus(code = HttpStatus.OK)
	public CompletableFuture<List<Subcategory>> getSubcategoriesbyRecipeBook(@PathVariable Long idRecipeBook) {
		return CompletableFuture.supplyAsync(() -> subcategorySvc.getSubcategoriesByRecipeBook(idRecipeBook));
	}

	// Get subcategories by category
	@CircuitBreaker(name = "subcategoryBreaker", fallbackMethod = "getListObjectCB")
	@TimeLimiter(name = "subcategoryBreaker")
	@GetMapping("/subcategory/category/{idCategory}")
	@ResponseStatus(code = HttpStatus.OK)
	public CompletableFuture<List<Subcategory>> getSubcategoriesbyCategory(@PathVariable Long idCategory) {
		return CompletableFuture.supplyAsync(() -> subcategorySvc.getSubcategoriesByCategory(idCategory));
	}

	// (CircuitBreaker) Get void circuit breaker
	public CompletableFuture<Void> getVoidCB(Throwable t) {
		return CompletableFuture.runAsync(() -> logger.error("Enabled subcategory breaker" + t));
	}

	// (CircuitBreaker) Get object circuit breaker
	public CompletableFuture<Subcategory> getObjectCB(Throwable t) {
		logger.error("Enabled subcategory breaker" + t);
		return CompletableFuture.supplyAsync(() -> {
			Subcategory subcategory = new Subcategory();
			subcategory.setIdSubcategory(null);
			subcategory.setName(null);
			subcategory.setCategory(null);
			return subcategory;
		});
	}

	// (CircuitBreaker) Get list object circuit breaker
	public CompletableFuture<List<Subcategory>> getListObjectCB(Throwable t) {
		logger.error("Enabled subcategory breaker" + t);
		return CompletableFuture.supplyAsync(() -> {
			List<Subcategory> list = new ArrayList<Subcategory>();
			Subcategory subcategory = new Subcategory();
			subcategory.setIdSubcategory(null);
			subcategory.setName(null);
			subcategory.setCategory(null);
			list.add(subcategory);
			return list;
		});
	}
	
	// (CircuitBreaker) Get map integer circuit breaker
	public CompletableFuture<Map<String, Integer>> getMapIntegerCB(Throwable t) {
		logger.error("Enabled subcategory breaker " + t);
		return CompletableFuture.supplyAsync(() -> {
			Map<String, Integer> jsonObject = new HashMap<String, Integer>();
			jsonObject.put("count", null);
			return jsonObject;
		});
	}

}
