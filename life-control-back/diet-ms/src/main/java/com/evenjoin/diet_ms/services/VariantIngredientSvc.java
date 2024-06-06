package com.evenjoin.diet_ms.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.evenjoin.diet_ms.entity.VariantIngredient;
import com.evenjoin.diet_ms.repository.VariantIngredientRepo;
import com.evenjoin.diet_ms.services.interfaces.IVariantIngredientSvc;

@Service
public class VariantIngredientSvc implements IVariantIngredientSvc {


	@Autowired
	private VariantIngredientRepo variantIngredientRepo;
	
	@Override
	public List<VariantIngredient> getVariantIngredients() {
		return variantIngredientRepo.findAll();
	}

	@Override
	public VariantIngredient getVariantIngredient(Long idVariantIngredient) {
		return variantIngredientRepo.findById(idVariantIngredient).orElse(null);
	}

	@Override
	public VariantIngredient addVariantIngredient(VariantIngredient variantIngredient) {
		return variantIngredientRepo.save(variantIngredient);
	}

	@Override
	public void deleteVariantIngredient(Long idVariantIngredient) {
		variantIngredientRepo.deleteById(idVariantIngredient);
	}
	
}
