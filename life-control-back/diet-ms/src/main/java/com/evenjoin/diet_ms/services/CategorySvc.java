package com.evenjoin.diet_ms.services;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.evenjoin.diet_ms.entity.Category;
import com.evenjoin.diet_ms.repository.CategoryRepo;
import com.evenjoin.diet_ms.services.interfaces.ICategorySvc;

@Service
public class CategorySvc implements ICategorySvc{

	@Autowired
	private CategoryRepo categoryRepo;
	
	@Override
	public List<Category> getCategories() {
		return categoryRepo.findAll();
	}

	@Override
	public Category getCategory(Long idCategory) {
		return categoryRepo.findById(idCategory).orElse(null);
	}

	@Override
	public Category addCategory(Category category) {
		return categoryRepo.save(category);
	}

	@Override
	public void deleteCategory(Long idCategory) {
		categoryRepo.deleteById(idCategory);
	}

	@Override
	public List<Object[]> getCategoriesByOwner(String owner) {
		return categoryRepo.getCategoriesByOwner(owner);
	}

}
