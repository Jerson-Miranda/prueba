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
import com.evenjoin.diet_ms.entity.RecipeBook;
import com.evenjoin.diet_ms.services.RecipeBookSvc;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;

@RefreshScope
@RestController
@RequestMapping("/diet")
public class RecipeBookCtrl {

    private static final Logger logger = LoggerFactory.getLogger(RecipeBookCtrl.class);

    @Autowired
    private RecipeBookSvc recipeBookSvc;

    // Get all recipe books
    @CircuitBreaker(name = "recipeBookBreaker", fallbackMethod = "getListObjectCB")
    @TimeLimiter(name = "recipeBookBreaker")
    @GetMapping("/recipe-book/all")
    @ResponseStatus(code = HttpStatus.OK)
    public CompletableFuture<List<RecipeBook>> getRecipeBooks() {
        return CompletableFuture.supplyAsync(() -> recipeBookSvc.getRecipeBooks());
    }

    // Get a recipe book
    @CircuitBreaker(name = "recipeBookBreaker", fallbackMethod = "getObjectCB")
    @TimeLimiter(name = "recipeBookBreaker")
    @GetMapping("/recipe-book/{idRecipeBook}")
    @ResponseStatus(code = HttpStatus.OK)
    public CompletableFuture<RecipeBook> getRecipeBook(@PathVariable Long idRecipeBook) {
        return CompletableFuture.supplyAsync(() -> recipeBookSvc.getRecipeBook(idRecipeBook));
    }

    // Add a recipe book
    @CircuitBreaker(name = "recipeBookBreaker", fallbackMethod = "getObjectCB")
    @TimeLimiter(name = "recipeBookBreaker")
    @PostMapping("/recipe-book/add")
    @ResponseStatus(code = HttpStatus.CREATED)
    public CompletableFuture<RecipeBook> addRecipeBook(@RequestBody RecipeBook recipeBook) {
        return CompletableFuture.supplyAsync(() -> recipeBookSvc.addRecipeBook(recipeBook));
    }

    // Update a recipe
    @CircuitBreaker(name = "recipeBookBreaker", fallbackMethod = "getObjectCB")
    @TimeLimiter(name = "recipeBookBreaker")
    @PutMapping("/recipe-book/update/{idRecipeBook}")
    @ResponseStatus(code = HttpStatus.OK)
    public CompletableFuture<RecipeBook> updateRecipeBook(@RequestBody RecipeBook recipeBook,
            @PathVariable Long idRecipeBook) {
        return CompletableFuture.supplyAsync(() -> {
            RecipeBook currentRecipeBook = recipeBookSvc.getRecipeBook(idRecipeBook);
            currentRecipeBook.setName(recipeBook.getName());
            currentRecipeBook.setDescription(recipeBook.getDescription());
            currentRecipeBook.setPhoto(recipeBook.getPhoto());
            currentRecipeBook.setNumPages(recipeBook.getNumPages());
            currentRecipeBook.setIsFavorite(recipeBook.getIsFavorite());
            return recipeBookSvc.addRecipeBook(currentRecipeBook);
        });
    }

    // Delete a recipe book
    @CircuitBreaker(name = "recipeBookBreaker", fallbackMethod = "getVoidCB")
    @TimeLimiter(name = "recipeBookBreaker")
    @DeleteMapping("/recipe-book/{idRecipeBook}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public CompletableFuture<Void> deleteRecipeBook(@PathVariable Long idRecipeBook) {
        return CompletableFuture.runAsync(() -> recipeBookSvc.deleteRecipeBook(idRecipeBook));
    }

    // Count recipe books
    @CircuitBreaker(name = "recipeBookBreaker", fallbackMethod = "getMapIntegerCB")
    @TimeLimiter(name = "recipeBookBreaker")
    @GetMapping("/recipe-book/count")
    @ResponseStatus(code = HttpStatus.OK)
    public CompletableFuture<Map<String, Integer>> countRecipeBooks() {
        return CompletableFuture.supplyAsync(() -> {
            Map<String, Integer> jsonObject = new HashMap<String, Integer>();
            jsonObject.put("count", recipeBookSvc.countRecipeBooks());
            return jsonObject;
        });
    }

    // Get recipe book by recipe
    @CircuitBreaker(name = "recipeBookBreaker", fallbackMethod = "getObjectCB")
    @TimeLimiter(name = "recipeBookBreaker")
    @GetMapping("/recipe-book/recipe/{idRecipe}")
    @ResponseStatus(code = HttpStatus.OK)
    public CompletableFuture<RecipeBook> getRecipeBookByRecipe(@PathVariable Long idRecipe) {
        return CompletableFuture.supplyAsync(() -> recipeBookSvc.getRecipeBookByRecipe(idRecipe));
    }

    // Get favorite recipe books
    @CircuitBreaker(name = "recipeBookBreaker", fallbackMethod = "getListObjectCB")
    @TimeLimiter(name = "recipeBookBreaker")
    @GetMapping("/recipe-book/favorite")
    @ResponseStatus(code = HttpStatus.OK)
    public CompletableFuture<List<RecipeBook>> getFavoriteRecipeBooks() {
        return CompletableFuture.supplyAsync(() -> recipeBookSvc.getFavoriteRecipeBooks());
    }

    // (CircuitBreaker) Get void circuit breaker
    public CompletableFuture<Void> getVoidCB(Throwable t) {
        return CompletableFuture.runAsync(() -> logger.error("Enabled recipe book breaker" + t));
    }

    // (CircuitBreaker) Get object circuit breaker
    public CompletableFuture<RecipeBook> getObjectCB(Throwable t) {
        logger.error("Enabled recipe book breaker" + t);
        return CompletableFuture.supplyAsync(() -> {
            RecipeBook recipeBook = new RecipeBook();
            recipeBook.setIdRecipeBook(null);
            recipeBook.setName(null);
            recipeBook.setDescription(null);
            recipeBook.setPhoto(null);
            recipeBook.setNumPages(null);
            recipeBook.setIsFavorite(null);
            return recipeBook;
        });
    }

    // (CircuitBreaker) Get list object circuit breaker
    public CompletableFuture<List<RecipeBook>> getListObjectCB(Throwable t) {
        logger.error("Enabled recipe book breaker" + t);
        return CompletableFuture.supplyAsync(() -> {
            List<RecipeBook> list = new ArrayList<RecipeBook>();
            RecipeBook recipeBook = new RecipeBook();
            recipeBook.setIdRecipeBook(null);
            recipeBook.setName(null);
            recipeBook.setDescription(null);
            recipeBook.setPhoto(null);
            recipeBook.setNumPages(null);
            recipeBook.setIsFavorite(null);
            list.add(recipeBook);
            return list;
        });
    }

    // (CircuitBreaker) Get map integer circuit breaker
    public CompletableFuture<Map<String, Integer>> getMapIntegerCB(Throwable t) {
        logger.error("Enabled recipe book breaker " + t);
        return CompletableFuture.supplyAsync(() -> {
            Map<String, Integer> jsonObject = new HashMap<String, Integer>();
            jsonObject.put("count", null);
            return jsonObject;
        });
    }
}
