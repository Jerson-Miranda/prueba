package com.evenjoin.diet_ms.services.interfaces;

import java.util.List;

import com.evenjoin.diet_ms.entity.Macronutrient;

public interface IMacronutrientSvc {

	public List<Macronutrient> getMacronutrients();
	public Macronutrient getMacronutrient(Long idMacronutrient);
	public Macronutrient addMacronutrient(Macronutrient macronutrient);
	public void deleteMacronutrient(Long idMacronutrient);
	public Macronutrient getMacronutrientsByIngredient(String barcode);

}
