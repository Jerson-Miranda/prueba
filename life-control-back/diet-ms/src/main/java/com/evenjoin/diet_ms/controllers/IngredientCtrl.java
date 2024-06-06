package com.evenjoin.diet_ms.controllers;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import com.evenjoin.diet_ms.entity.Ingredient;
import com.evenjoin.diet_ms.services.IngredientSvc;

@RefreshScope
@RestController
@RequestMapping("/diet")
public class IngredientCtrl {

	@Autowired
	private IngredientSvc ingredientSvc;
	
	//Get all ingredients
	@GetMapping("/ingredient/all")
	@ResponseStatus(code = HttpStatus.OK)
	private List<Ingredient> getIngredients() {
		return ingredientSvc.getIngredients();
	}
	
	//Get a ingredient
	@GetMapping("/ingredient/{idIngredient}")
	@ResponseStatus(code = HttpStatus.OK)
	private Ingredient getIngredient(@PathVariable Long idIngredient) {
		return ingredientSvc.getIngredient(idIngredient);
	}
	
	//Add a ingredient
	@PostMapping("/ingredient/add")
	@ResponseStatus(code = HttpStatus.CREATED)
	private Ingredient addIngredient(@RequestBody Ingredient ingredient) {
		return ingredientSvc.addIngredient(ingredient);
	}
	
	//Update a ingredient
	@PutMapping("/ingredient/update/{idIngredient}")
	@ResponseStatus(code = HttpStatus.OK)
	private Ingredient updateIngredient(@RequestBody Ingredient ingredient, @PathVariable Long idIngredient) {
		Ingredient currentIngredient = ingredientSvc.getIngredient(idIngredient);
		currentIngredient.setBarcode(ingredient.getBarcode());
		currentIngredient.setBrand(ingredient.getBrand());
		currentIngredient.setName(ingredient.getName());
		currentIngredient.setPhoto(ingredient.getPhoto());
		currentIngredient.setDescription(ingredient.getDescription());
		currentIngredient.setNutritionalFact(ingredient.getNutritionalFact());
		currentIngredient.setSubcategory(ingredient.getSubcategory());
		currentIngredient.setTypeIngredient(ingredient.getTypeIngredient());
		return ingredientSvc.addIngredient(currentIngredient);
	}
	
	//Delete a ingredient
	@DeleteMapping("/ingredient/{idIngredient}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	private void deleteIngredient(@PathVariable Long idIngredient) {
		ingredientSvc.deleteIngredient(idIngredient);
	}
	
	//Get ingredients by category
	@GetMapping("/ingredient/category/{idCategory}")
	@ResponseStatus(code = HttpStatus.OK)
	public List<Ingredient> getIngredientsByCategory(@PathVariable Long idCategory){
		return ingredientSvc.getIngredientsByCategory(idCategory);
	}
	
	//Get ingredients by subcategory
	@GetMapping("/ingredient/subcategory/{idSubcategory}")
	@ResponseStatus(code = HttpStatus.OK)
	public List<Ingredient> getIngredientsBySubcategory(@PathVariable Long idSubcategory){
		return ingredientSvc.getIngredientsBySubcategory(idSubcategory);
	}
	
	//Get ingredient with maximum nutrient
	@GetMapping("/ingredient/max/{nutrient}")
	@ResponseStatus(code = HttpStatus.OK)
	public Ingredient getIngredientWithMaxNutrient(@PathVariable String nutrient) {
	    return ingredientSvc.getIngredientWithMaxNutrient(nutrient);
	}
	
	//Get ingredient with minimum nutrient
	@GetMapping("/ingredient/min/{nutrient}")
	@ResponseStatus(code = HttpStatus.OK)
	public Ingredient getIngredientWithMinNutrient(@PathVariable String nutrient) {
	    return ingredientSvc.getIngredientWithMinNutrient(nutrient);
	}
	
	//Get quantity to consume by ingredient
	@GetMapping("/ingredient/quantity/{idIngredient}")
	@ResponseStatus(code = HttpStatus.OK)
	public Map<String, BigDecimal> getQuantityToConsume(@PathVariable Long idIngredient) {
		BigDecimal responde = ingredientSvc.getQuantityToConsume(idIngredient);
		Map<String, BigDecimal> jsonObject = new HashMap<String, BigDecimal>();
		jsonObject.put("quantity", responde);
	    return jsonObject;
	}
	
//	//Get ingredient with max nutrient
//	@GetMapping("/ingredient/min/{nutrient}")
//	@ResponseStatus(code = HttpStatus.OK)
//	public Map<String, Object> getIngredientWithMinNutrient(@PathVariable String nutrient) {
//	    Object response = ingredientSvc.getIngredientWithMinNutrient(nutrient);
//	    if (response instanceof Object[]) {
//	        Object[] responseData = (Object[]) response;
//	            Map<String, Object> resultMap = new HashMap<>();
//	            resultMap.put("name", responseData[0]);
//	            resultMap.put(nutrient, responseData[1]);
//	            resultMap.put("photo", responseData[2]);
//	            return resultMap;
//	    }
//	    return Collections.emptyMap();
//	}
	
}
