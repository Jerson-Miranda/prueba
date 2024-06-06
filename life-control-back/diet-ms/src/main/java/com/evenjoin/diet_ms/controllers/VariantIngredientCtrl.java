package com.evenjoin.diet_ms.controllers;

import java.util.List;

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

import com.evenjoin.diet_ms.entity.VariantIngredient;
import com.evenjoin.diet_ms.services.VariantIngredientSvc;

@RefreshScope
@RestController
@RequestMapping("/diet")
public class VariantIngredientCtrl {

	@Autowired
	private VariantIngredientSvc variantIngredientSvc;
	
	//Get all variant ingredients
	@GetMapping("/variant-ingredient/all")
	@ResponseStatus(code = HttpStatus.OK)
	private List<VariantIngredient> getVariantIngredients() {
		return variantIngredientSvc.getVariantIngredients();
	}
	
	//Get a variant ingredient
	@GetMapping("/variant-ingredient/{idVariantIngredient}")
	@ResponseStatus(code = HttpStatus.OK)
	private VariantIngredient getVariantIngredient(@PathVariable Long idVariantIngredient) {
		return variantIngredientSvc.getVariantIngredient(idVariantIngredient);
	}
	
	//Add a variant ingredient
	@PostMapping("/variant-ingredient/add")
	@ResponseStatus(code = HttpStatus.CREATED)
	private VariantIngredient addVariantIngredient(@RequestBody VariantIngredient variantIngredient) {
		return variantIngredientSvc.addVariantIngredient(variantIngredient);
	}
	
	//Update a variant ingredient
	@PutMapping("/variant-ingredient/update/{idVariantIngredient}")
	@ResponseStatus(code = HttpStatus.OK)
	private VariantIngredient updateVariantIngredient(@RequestBody VariantIngredient variantIngredient, @PathVariable Long idVariantIngredient) {
		VariantIngredient currentVariantIngredient = variantIngredientSvc.getVariantIngredient(idVariantIngredient);
		currentVariantIngredient.setGrMlPza(variantIngredient.getGrMlPza());
		currentVariantIngredient.setPrice(variantIngredient.getPrice());
		currentVariantIngredient.setIngredient(variantIngredient.getIngredient());
		return variantIngredientSvc.addVariantIngredient(currentVariantIngredient);
	}
	
	//Delete a variant ingredient
	@DeleteMapping("/variant-ingredient/{idVariantIngredient}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	private void deleteVariantIngredient(@PathVariable Long idVariantIngredient) {
		variantIngredientSvc.deleteVariantIngredient(idVariantIngredient);
	}
	
}
