package com.evenjoin.diet_ms.services.interfaces;

import java.util.List;

import com.evenjoin.diet_ms.entity.TypeIngredient;

public interface ITypeIngredientSvc {

	public List<TypeIngredient> getTypeIngredients();
	public TypeIngredient getTypeIngredient(Long idTypeIngredient);
	public TypeIngredient addTypeIngredient(TypeIngredient typeIngredient);
	public void deleteTypeIngredient(Long idTypeIngredient);
	
}
