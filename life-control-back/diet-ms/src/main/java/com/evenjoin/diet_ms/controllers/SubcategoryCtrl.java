package com.evenjoin.diet_ms.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

@RefreshScope
@RestController
@RequestMapping("/diet")
public class SubcategoryCtrl {

	@Autowired
	private SubcategorySvc subcategorySvc;
	
	//Get all subcategories
	@GetMapping("/subcategory/all")
	@ResponseStatus(code = HttpStatus.OK)
	private List<Subcategory> getSubcategories() {
		return subcategorySvc.getSubcategories();
	}
	
	//Get a subcategory
	@GetMapping("/subcategory/{idSubcategory}")
	@ResponseStatus(code = HttpStatus.OK)
	private Subcategory getSubcategory(@PathVariable Long idSubcategory) {
		return subcategorySvc.getSubcategory(idSubcategory);
	}
	
	//Add a subcategory
	@PostMapping("/subcategory/add")
	@ResponseStatus(code = HttpStatus.CREATED)
	private Subcategory addSubcategory(@RequestBody Subcategory subcategory) {
		return subcategorySvc.addSubcategory(subcategory);
	}
	
	//Update a subcategory
	@PutMapping("/subcategory/update/{idSubcategory}")
	@ResponseStatus(code = HttpStatus.OK)
	private Subcategory updateSubcategory(@RequestBody Subcategory subcategory, @PathVariable Long idSubcategory) {
		Subcategory currentSubcategory = subcategorySvc.getSubcategory(idSubcategory);
		currentSubcategory.setName(subcategory.getName());
		currentSubcategory.setCategory(subcategory.getCategory());
		return subcategorySvc.addSubcategory(currentSubcategory);
	}
	
	//Delete a subcategory
	@DeleteMapping("/subcategory/{idSubcategory}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	private void deleteSubcategory(@PathVariable Long idSubcategory) {
		subcategorySvc.deleteSubcategory(idSubcategory);
	}
	
	//Get subcategories by owner
	@GetMapping("/subcategory/owner/{owner}")
	public List<Map<String, Object>> getSubcategoriesbyOwner(@PathVariable String owner) {
		List<Object[]> response = subcategorySvc.getSubcategoriesByOwner(owner);
		List<Map<String, Object>> json = new ArrayList<Map<String,Object>>();
		for (Object[] res : response) {
			Map<String, Object> jsonObject = new HashMap<String, Object>();
			jsonObject.put("idSubcategory", res[0]);
			jsonObject.put("name", res[1]);
			json.add(jsonObject);
		}
		return json;
	}
	
}
