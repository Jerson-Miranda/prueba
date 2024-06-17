package com.evenjoin.diet_ms.services.interfaces;

import java.util.Date;
import java.util.List;

import com.evenjoin.diet_ms.entity.Macronutrient;

public interface IMacronutrientSvc {

	public List<Macronutrient> getMacronutrients();
	public Macronutrient getMacronutrient(Long idMacronutrient);
	public Macronutrient addMacronutrient(Macronutrient macronutrient);
	public void deleteMacronutrient(Long idMacronutrient);
	public Macronutrient getMacronutrientsByIngredient(Long idIngredient);
	public Object getMacronutrientsByRecipe(Long idRecipe);
	public Object getMacronutrientsByDiet(Long idDiet);
	public List<Object> getMacronutrientsByDietRange(Date startDate, Date endDate);

}
