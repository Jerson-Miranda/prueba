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
import com.evenjoin.diet_ms.entity.NutritionalFact;
import com.evenjoin.diet_ms.services.NutritionalFactSvc;

@RefreshScope
@RestController
@RequestMapping("/diet")
public class NutritionalFactCtrl {

	@Autowired
	private NutritionalFactSvc nutritionalFactSvc;
	
	//Get all nutritional facts
	@GetMapping("/nutritional-fact/all")
	@ResponseStatus(code = HttpStatus.OK)
	private List<NutritionalFact> getNutritionalFacts() {
		return nutritionalFactSvc.getNutritionalFacts();
	}
	
	//Get a nutritional fact
	@GetMapping("/nutritional-fact/{idNutritionalFact}")
	@ResponseStatus(code = HttpStatus.OK)
	private NutritionalFact getNutritionalFact(@PathVariable Long idNutritionalFact) {
		return nutritionalFactSvc.getNutritionalFact(idNutritionalFact);
	}
	
	//Add a nutritional fact
	@PostMapping("/nutritional-fact/add")
	@ResponseStatus(code = HttpStatus.CREATED)
	private NutritionalFact addNutritionalFact(@RequestBody NutritionalFact nutritionalFact) {
		return nutritionalFactSvc.addNutritionalFact(nutritionalFact);
	}
	
	//Update a nutritional fact
	@PutMapping("/nutritional-fact/update/{idNutritionalFact}")
	@ResponseStatus(code = HttpStatus.OK)
	private NutritionalFact updateNutritionalFact(@RequestBody NutritionalFact nutritionalFact, @PathVariable Long idNutritionalFact) {
		NutritionalFact currentNutritionalFact = nutritionalFactSvc.getNutritionalFact(idNutritionalFact);
		currentNutritionalFact.setKcal(nutritionalFact.getKcal());
		currentNutritionalFact.setProtein(nutritionalFact.getProtein());
		currentNutritionalFact.setCarbohydrate(nutritionalFact.getCarbohydrate());
		currentNutritionalFact.setSugar(nutritionalFact.getSugar());
		currentNutritionalFact.setAddedSugar(nutritionalFact.getAddedSugar());
		currentNutritionalFact.setFat(nutritionalFact.getFat());
		currentNutritionalFact.setSaturatedFat(nutritionalFact.getSaturatedFat());
		currentNutritionalFact.setTrans(nutritionalFact.getTrans());
		currentNutritionalFact.setFiber(nutritionalFact.getFiber());
		currentNutritionalFact.setSodium(nutritionalFact.getSodium());
		currentNutritionalFact.setPortion(nutritionalFact.getPortion());
		return nutritionalFactSvc.addNutritionalFact(currentNutritionalFact);
	}
	
	//Delete a nutritional fact
	@DeleteMapping("/nutritional-fact/{idNutritionalFact}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	private void deleteNutritionalFact(@PathVariable Long idNutritionalFact) {
		nutritionalFactSvc.deleteNutritionalFact(idNutritionalFact);
	}
	
}
