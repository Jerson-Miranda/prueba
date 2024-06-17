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
import com.evenjoin.diet_ms.entity.Category;
import com.evenjoin.diet_ms.services.CategorySvc;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;

@RefreshScope
@RestController
@RequestMapping("/diet")
public class CategoryCtrl {

	private static final Logger logger = LoggerFactory.getLogger(CategoryCtrl.class);

	@Autowired
	private CategorySvc categorySvc;

	// Get all categories
	@CircuitBreaker(name = "categoryBreaker", fallbackMethod = "getListObjectCB")
	@TimeLimiter(name = "categoryBreaker")
	@GetMapping("/category/all")
	@ResponseStatus(code = HttpStatus.OK)
	public CompletableFuture<List<Category>> getCategories() {
		return CompletableFuture.supplyAsync(() -> categorySvc.getCategories());
	}

	// Get a category
	@CircuitBreaker(name = "categoryBreaker", fallbackMethod = "getObjectCB")
	@TimeLimiter(name = "categoryBreaker")
	@GetMapping("/category/{idCategory}")
	@ResponseStatus(code = HttpStatus.OK)
	public CompletableFuture<Category> getCategory(@PathVariable Long idCategory) {
		return CompletableFuture.supplyAsync(() -> categorySvc.getCategory(idCategory));
	}

	// Add a category
	@CircuitBreaker(name = "categoryBreaker", fallbackMethod = "getObjectCB")
	@TimeLimiter(name = "categoryBreaker")
	@PostMapping("/category/add")
	@ResponseStatus(code = HttpStatus.CREATED)
	public CompletableFuture<Category> addCategory(@RequestBody Category category) {
		return CompletableFuture.supplyAsync(() -> categorySvc.addCategory(category));
	}

	// Update a category
	@CircuitBreaker(name = "categoryBreaker", fallbackMethod = "getObjectCB")
	@TimeLimiter(name = "categoryBreaker")
	@PutMapping("/category/update/{idCategory}")
	@ResponseStatus(code = HttpStatus.OK)
	public CompletableFuture<Category> updateCategory(@RequestBody Category category, @PathVariable Long idCategory) {
		return CompletableFuture.supplyAsync(() -> {
			Category currentCategory = categorySvc.getCategory(idCategory);
			currentCategory.setName(category.getName());
			currentCategory.setRecipeBook(category.getRecipeBook());
			return categorySvc.addCategory(currentCategory);
		});
	}

	// Delete a category
	@CircuitBreaker(name = "categoryBreaker", fallbackMethod = "getVoidCB")
	@TimeLimiter(name = "categoryBreaker")
	@DeleteMapping("/category/{idCategory}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public CompletableFuture<Void> deleteCategory(@PathVariable Long idCategory) {
		return CompletableFuture.runAsync(() -> categorySvc.deleteCategory(idCategory));
	}
	
	// Count categories
	@CircuitBreaker(name = "categoryBreaker", fallbackMethod = "getMapIntegerCB")
	@TimeLimiter(name = "categoryBreaker")
	@GetMapping("/category/count")
	@ResponseStatus(code = HttpStatus.OK)
	public CompletableFuture<Map<String, Integer>> countCategories(){
		return CompletableFuture.supplyAsync(()-> {
			Map<String, Integer> jsonObject = new HashMap<String, Integer>();
			jsonObject.put("count", categorySvc.countCategories());
			return jsonObject;
		});
	}
	
	// Get categories by recipe book
	@CircuitBreaker(name = "categoryBreaker", fallbackMethod = "getListObjectCB")
	@TimeLimiter(name = "categoryBreaker")
	@GetMapping("/category/recipe-book/{idRecipeBook}")
	@ResponseStatus(code = HttpStatus.OK)
	public CompletableFuture<List<Category>> getCategoriesByRecipeBook(@PathVariable Long idRecipeBook) {
		return CompletableFuture.supplyAsync(() -> {
			return categorySvc.getCategoriesByRecipeBook(idRecipeBook);
		});
	}

	// (CircuitBreaker) Get void circuit breaker
	public CompletableFuture<Void> getVoidCB(Throwable t) {
		return CompletableFuture.runAsync(() -> logger.error("Error while deleting " + t));
	}

	// (CircuitBreaker) Get object circuit breaker
	public CompletableFuture<Category> getObjectCB(Throwable t) {
		logger.error("Enabled category breaker " + t);
		return CompletableFuture.supplyAsync(() -> {
			Category category = new Category();
			category.setIdCategory(null);
			category.setName(null);
			category.setRecipeBook(null);
			return category;
		});
	}

	// (CircuitBreaker) Get list object circuit breaker
	public CompletableFuture<List<Category>> getListObjectCB(Throwable t) {
		logger.error("Enabled category breaker " + t);
		return CompletableFuture.supplyAsync(() -> {
			Category category = new Category();
			List<Category> list = new ArrayList<Category>();
			category.setIdCategory(null);
			category.setName(null);
			category.setRecipeBook(null);
			list.add(category);
			return list;
		});
	}

	// (CircuitBreaker) Get map integer circuit breaker
	public CompletableFuture<Map<String, Integer>> getMapIntegerCB(Throwable t) {
		logger.error("Enabled category breaker " + t);
		return CompletableFuture.supplyAsync(() -> {
			Map<String, Integer> jsonObject = new HashMap<String, Integer>();
			jsonObject.put("count", null);
			return jsonObject;
		});
	}

}
