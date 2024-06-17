package com.evenjoin.diet_ms.services;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.evenjoin.diet_ms.entity.Macronutrient;
import com.evenjoin.diet_ms.repository.MacronutrientRepo;
import com.evenjoin.diet_ms.services.interfaces.IMacronutrientSvc;

@Service
public class MacronutrientSvc implements IMacronutrientSvc {

	@Autowired
	private MacronutrientRepo macronutrientRepo;

	@Override
	@Transactional(readOnly = true)
	public List<Macronutrient> getMacronutrients() {
		return macronutrientRepo.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Macronutrient getMacronutrient(Long idMacronutrient) {
		return macronutrientRepo.findById(idMacronutrient).orElse(null);
	}

	@Override
	@Transactional
	public Macronutrient addMacronutrient(Macronutrient macronutrient) {
		return macronutrientRepo.save(macronutrient);
	}

	@Override
	@Transactional
	public void deleteMacronutrient(Long idMacronutrient) {
		macronutrientRepo.deleteById(idMacronutrient);
	}

	@Override
	@Transactional(readOnly = true)
	public Macronutrient getMacronutrientsByIngredient(Long idIngredient) {
		return macronutrientRepo.getMacronutrientsByIngredient(idIngredient);
	}

	@Override
	@Transactional(readOnly = true)
	public Object getMacronutrientsByRecipe(Long idRecipe) {
		return macronutrientRepo.getMacronutrientsByRecipe(idRecipe);
	}

	@Override
	@Transactional(readOnly = true)
	public Object getMacronutrientsByDiet(Long idDiet) {
		return macronutrientRepo.getMacronutrientsByDiet(idDiet);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Object> getMacronutrientsByDietRange(Date startDate, Date endDate) {
		return macronutrientRepo.getMacronutrientsByDietRange(startDate, endDate);
	}
}
