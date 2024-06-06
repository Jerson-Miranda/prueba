package com.evenjoin.diet_ms.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.evenjoin.diet_ms.entity.Subcategory;
import com.evenjoin.diet_ms.repository.SubcategoryRepo;
import com.evenjoin.diet_ms.services.interfaces.ISubcategorySvc;

@Service
public class SubcategorySvc implements ISubcategorySvc {

	@Autowired
	private SubcategoryRepo subcategoryRepo;

	@Override
	public List<Subcategory> getSubcategories() {
		return subcategoryRepo.findAll();
	}

	@Override
	public Subcategory getSubcategory(Long idSubcategory) {
		return subcategoryRepo.findById(idSubcategory).orElse(null);
	}

	@Override
	public Subcategory addSubcategory(Subcategory subcategory) {
		return subcategoryRepo.save(subcategory);
	}

	@Override
	public void deleteSubcategory(Long idSubcategory) {
		subcategoryRepo.deleteById(idSubcategory);
	}

	@Override
	public List<Object[]> getSubcategoriesByOwner(String owner) {
		return subcategoryRepo.getSubcategoriesByOwner(owner);
	}
}
