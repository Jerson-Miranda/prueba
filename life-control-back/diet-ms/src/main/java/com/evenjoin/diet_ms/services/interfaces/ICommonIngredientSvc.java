package com.evenjoin.diet_ms.services.interfaces;

import java.util.List;

import com.evenjoin.diet_ms.entity.CommonIngredient;

public interface ICommonIngredientSvc {
	
	public List<CommonIngredient> getCommonIngredients();
	public CommonIngredient getCommonIngredient(Long idCommonIngredient);
	public CommonIngredient addCommonIngredient(CommonIngredient commonIngredient);
	public void deleteCommonIngredient(Long idCommonIngredient);
	
}
