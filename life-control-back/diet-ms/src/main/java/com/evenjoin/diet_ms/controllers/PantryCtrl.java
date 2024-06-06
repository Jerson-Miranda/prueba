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

import com.evenjoin.diet_ms.entity.Pantry;
import com.evenjoin.diet_ms.services.PantrySvc;

@RefreshScope
@RestController
@RequestMapping("/diet")
public class PantryCtrl {

	@Autowired
	private PantrySvc pantrySvc;

	//Get all pantries
	@GetMapping("/pantry/all")
	@ResponseStatus(code = HttpStatus.OK)
	private List<Pantry> getPantries() {
		return pantrySvc.getPantries();
	}
	
	//Get a pantry
	@GetMapping("/pantry/{idPantry}")
	@ResponseStatus(code = HttpStatus.OK)
	private Pantry getPantry(@PathVariable Long idPantry) {
		return pantrySvc.getPantry(idPantry);
	}
	
	//Add a pantry
	@PostMapping("/pantry/add")
	@ResponseStatus(code = HttpStatus.CREATED)
	private Pantry addPantry(@RequestBody Pantry pantry) {
		return pantrySvc.addPantry(pantry);
	}
	
	//Update a pantry
	@PutMapping("/pantry/update/{idPantry}")
	@ResponseStatus(code = HttpStatus.OK)
	private Pantry updatePantry(@RequestBody Pantry pantry, @PathVariable Long idPantry) {
		Pantry currentPantry = pantrySvc.getPantry(idPantry);
		currentPantry.setExpirationDate(pantry.getExpirationDate());
		currentPantry.setStock(pantry.getStock());
		currentPantry.setIngredient(pantry.getIngredient());
		return pantrySvc.addPantry(currentPantry);
	}
	
	//Delete a pantry
	@DeleteMapping("/pantry/{idPantry}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	private void deletePantry(@PathVariable Long idPantry) {
		pantrySvc.deletePantry(idPantry);
	}
	
}
