package com.evenjoin.diet_ms.services.interfaces;

import java.util.List;

import com.evenjoin.diet_ms.entity.VariantIngredient;

public interface IVariantIngredientSvc {

	public List<VariantIngredient> getVariantIngredients();
	public VariantIngredient getVariantIngredient(Long idVariantIngredient);
	public VariantIngredient addVariantIngredient(VariantIngredient variantIngredient);
	public void deleteVariantIngredient(Long idVariantIngredient);
	
}
