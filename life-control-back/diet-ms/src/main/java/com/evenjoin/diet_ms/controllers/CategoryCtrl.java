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
	public CompletableFuture<Category> getCategory(@PathVariable Long idCategory) throws InterruptedException {
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
			currentCategory.setOwner(category.getOwner());
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

	// Get categories by owner
	@CircuitBreaker(name = "categoryBreaker", fallbackMethod = "getListMapCB")
	@TimeLimiter(name = "categoryBreaker")
	@GetMapping("/category/owner/{owner}")
	@ResponseStatus(code = HttpStatus.OK)
	public CompletableFuture<List<Map<String, Object>>> getCategoriesByOwner(@PathVariable String owner) {
		return CompletableFuture.supplyAsync(() -> {
			List<Object[]> response = categorySvc.getCategoriesByOwner(owner);
			List<Map<String, Object>> json = new ArrayList<Map<String, Object>>();
			for (Object[] res : response) {
				Map<String, Object> jsonObject = new HashMap<String, Object>();
				jsonObject.put("idCategory", res[0]);
				jsonObject.put("name", res[1]);
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
	public CompletableFuture<Category> getObjectCB(Throwable t) {
		logger.error("Enabled category breaker " + t);
		return CompletableFuture.supplyAsync(() -> {
			Category category = new Category();
			category.setIdCategory(null);
			category.setName(null);
			category.setOwner(null);
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
			category.setOwner(null);
			list.add(category);
			return list;
		});
	}

	// (CircuitBreaker) Get list map circuit breaker
	public CompletableFuture<List<Map<String, Object>>> getListMapCB(Throwable t) {
		logger.error("Enabled category breaker " + t);
		return CompletableFuture.supplyAsync(() -> {
			List<Map<String, Object>> json = new ArrayList<Map<String, Object>>();
			Map<String, Object> jsonObject = new HashMap<String, Object>();
			jsonObject.put("idCategory", null);
			jsonObject.put("name", null);
			jsonObject.put("owner", null);
			json.add(jsonObject);
			return json;
		});
	}

}
