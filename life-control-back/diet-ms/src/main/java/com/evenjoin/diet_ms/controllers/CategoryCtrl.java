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
import com.evenjoin.diet_ms.entity.Category;
import com.evenjoin.diet_ms.services.CategorySvc;

@RefreshScope
@RestController
@RequestMapping("/diet")
public class CategoryCtrl {

	@Autowired
	private CategorySvc categorySvc;
	
	//Get all categories
	@GetMapping("/category/all")
	@ResponseStatus(code = HttpStatus.OK)
	private List<Category> getCategories() {
		return categorySvc.getCategories();
	}
	
	//Get a category
	@GetMapping("/category/{idCategory}")
	@ResponseStatus(code = HttpStatus.OK)
	private Category getCategory(@PathVariable Long idCategory) {
		return categorySvc.getCategory(idCategory);
	}
	
	//Add a category
	@PostMapping("/category/add")
	@ResponseStatus(code = HttpStatus.CREATED)
	private Category addCategory(@RequestBody Category category) {
		return categorySvc.addCategory(category);
	}
	
	//Update a category
	@PutMapping("/category/update/{idCategory}")
	@ResponseStatus(code = HttpStatus.OK)
	private Category updateCategory(@RequestBody Category category, @PathVariable Long idCategory) {
		Category currentCategory = categorySvc.getCategory(idCategory);
		currentCategory.setName(category.getName());
		currentCategory.setOwner(category.getOwner());
		return categorySvc.addCategory(currentCategory);
	}
	
	//Delete a category
	@DeleteMapping("/category/{idCategory}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	private void deleteCategory(@PathVariable Long idCategory) {
		categorySvc.deleteCategory(idCategory);
	}
	
	@GetMapping("/category/owner/{owner}")
	@ResponseStatus(code = HttpStatus.OK)
	public List<Map<String, Object>> getCategoriesByOwner(@PathVariable String owner) {
	    List<Object[]> response = categorySvc.getCategoriesByOwner(owner);
	    List<Map<String, Object>> json = new ArrayList<Map<String, Object>>();
	    for (Object[] res : response) {
	        Map<String, Object> jsonObject = new HashMap<String, Object>();
	        jsonObject.put("idCategory", res[0]);
	        jsonObject.put("name", res[1]);
	        json.add(jsonObject);
	    }
	    return json;
	}

}
