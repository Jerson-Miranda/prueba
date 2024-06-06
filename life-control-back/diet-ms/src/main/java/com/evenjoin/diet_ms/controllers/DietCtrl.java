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
import com.evenjoin.diet_ms.entity.Diet;
import com.evenjoin.diet_ms.services.DietSvc;

@RefreshScope
@RestController
@RequestMapping("/diet")
public class DietCtrl {

	@Autowired
	private DietSvc dietSvc;
	
	//Get all diets
	@GetMapping("/all")
	@ResponseStatus(code = HttpStatus.OK)
	private List<Diet> getDiets() {
		return dietSvc.getDiets();
	}
	
	//Get a diet
	@GetMapping("/{idDiet}")
	@ResponseStatus(code = HttpStatus.OK)
	private Diet getDiet(@PathVariable Long idDiet) {
		return dietSvc.getDiet(idDiet);
	}
	
	//Add a diet
	@PostMapping("/add")
	@ResponseStatus(code = HttpStatus.CREATED)
	private Diet addDiet(@RequestBody Diet diet) {
		return dietSvc.addDiet(diet);
	}
	
	//Update a diet
	@PutMapping("/update/{idDiet}")
	@ResponseStatus(code = HttpStatus.OK)
	private Diet updateDiet(@RequestBody Diet diet, @PathVariable Long idDiet) {
		Diet currentDiet = dietSvc.getDiet(idDiet);
		currentDiet.setDate(diet.getDate());
		currentDiet.setTimeMinute(diet.getTimeMinute());
		return dietSvc.addDiet(currentDiet);
	}
	
	//Delete a diet
	@DeleteMapping("/{idDiet}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	private void deleteDiet(@PathVariable Long idDiet) {
		dietSvc.deleteDiet(idDiet);
	}
	
}
