package com.evenjoin.diet_ms.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.evenjoin.diet_ms.entity.CommonIngredient;
import com.evenjoin.diet_ms.repository.CommonIngredientRepo;
import com.evenjoin.diet_ms.services.interfaces.ICommonIngredientSvc;

@Service
public class CommonIngredientSvc implements ICommonIngredientSvc {

	@Autowired
	private CommonIngredientRepo commonIngredientRepo;
	
	@Override
	@Transactional(readOnly = true)
	public List<CommonIngredient> getCommonIngredients() {
		return commonIngredientRepo.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public CommonIngredient getCommonIngredient(Long idCommonIngredient) {
		return commonIngredientRepo.findById(idCommonIngredient).orElse(null);
	}

	@Override
	@Transactional
	public CommonIngredient addCommonIngredient(CommonIngredient variantIngredient) {
		return commonIngredientRepo.save(variantIngredient);
	}

	@Override
	@Transactional
	public void deleteCommonIngredient(Long idCommonIngredient) {
		commonIngredientRepo.deleteById(idCommonIngredient);
	}

}
