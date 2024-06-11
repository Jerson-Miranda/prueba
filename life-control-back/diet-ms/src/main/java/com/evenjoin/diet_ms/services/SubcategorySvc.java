package com.evenjoin.diet_ms.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.evenjoin.diet_ms.entity.Subcategory;
import com.evenjoin.diet_ms.repository.SubcategoryRepo;
import com.evenjoin.diet_ms.services.interfaces.ISubcategorySvc;

@Service
public class SubcategorySvc implements ISubcategorySvc {

	@Autowired
	private SubcategoryRepo subcategoryRepo;

	@Override
	@Transactional(readOnly = true)
	public List<Subcategory> getSubcategories() {
		return subcategoryRepo.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Subcategory getSubcategory(Long idSubcategory) {
		return subcategoryRepo.findById(idSubcategory).orElse(null);
	}

	@Override
	@Transactional
	public Subcategory addSubcategory(Subcategory subcategory) {
		return subcategoryRepo.save(subcategory);
	}

	@Override
	@Transactional
	public void deleteSubcategory(Long idSubcategory) {
		subcategoryRepo.deleteById(idSubcategory);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Subcategory> getSubcategoriesByRecipeBook(Long idRecipeBook) {
		return subcategoryRepo.getSubcategoriesByRecipeBook(idRecipeBook);
	}
}
