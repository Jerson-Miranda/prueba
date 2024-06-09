package com.evenjoin.diet_ms.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.evenjoin.diet_ms.entity.VariantIngredient;
import com.evenjoin.diet_ms.repository.VariantIngredientRepo;
import com.evenjoin.diet_ms.services.interfaces.IVariantIngredientSvc;

@Service
public class VariantIngredientSvc implements IVariantIngredientSvc {


	@Autowired
	private VariantIngredientRepo variantIngredientRepo;
	
	@Override
	@Transactional(readOnly = true)
	public List<VariantIngredient> getVariantIngredients() {
		return variantIngredientRepo.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public VariantIngredient getVariantIngredient(Long idVariantIngredient) {
		return variantIngredientRepo.findById(idVariantIngredient).orElse(null);
	}

	@Override
	@Transactional
	public VariantIngredient addVariantIngredient(VariantIngredient variantIngredient) {
		return variantIngredientRepo.save(variantIngredient);
	}

	@Override
	@Transactional
	public void deleteVariantIngredient(Long idVariantIngredient) {
		variantIngredientRepo.deleteById(idVariantIngredient);
	}
	
}
