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

import com.evenjoin.diet_ms.entity.TypeIngredient;
import com.evenjoin.diet_ms.services.TypeIngredientSvc;

@RefreshScope
@RestController
@RequestMapping("/diet")
public class TypeIngredientCtrl {

	@Autowired
	private TypeIngredientSvc typeIngredientSvc;
	
	//Get all type ingredients
	@GetMapping("/type-ingredient/all")
	@ResponseStatus(code = HttpStatus.OK)
	private List<TypeIngredient> getTypeIngredients() {
		return typeIngredientSvc.getTypeIngredients();
	}
	
	//Get a type ingredient
	@GetMapping("/type-ingredient/{idTypeIngredient}")
	@ResponseStatus(code = HttpStatus.OK)
	private TypeIngredient getTypeIngredient(@PathVariable Long idTypeIngredient) {
		return typeIngredientSvc.getTypeIngredient(idTypeIngredient);
	}
	
	//Add a type ingredient
	@PostMapping("/type-ingredient/add")
	@ResponseStatus(code = HttpStatus.CREATED)
	private TypeIngredient addTypeIngredient(@RequestBody TypeIngredient typeIngredient) {
		return typeIngredientSvc.addTypeIngredient(typeIngredient);
	}
	
	//Update a type ingredient
	@PutMapping("/type-ingredient/update/{idTypeIngredient}")
	@ResponseStatus(code = HttpStatus.OK)
	private TypeIngredient updateTypeIngredient(@RequestBody TypeIngredient typeIngredient, @PathVariable Long idTypeIngredient) {
		TypeIngredient currentTypeIngredient = typeIngredientSvc.getTypeIngredient(idTypeIngredient);
		currentTypeIngredient.setName(typeIngredient.getName());
		return typeIngredientSvc.addTypeIngredient(currentTypeIngredient);
	}
	
	//Delete a type ingredient
	@DeleteMapping("/type-ingredient/{idTypeIngredient}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	private void deleteTypeIngredient(@PathVariable Long idTypeIngredient) {
		typeIngredientSvc.deleteTypeIngredient(idTypeIngredient);
	}
	
}
