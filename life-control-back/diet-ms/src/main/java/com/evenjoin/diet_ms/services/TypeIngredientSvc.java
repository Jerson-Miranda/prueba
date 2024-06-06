package com.evenjoin.diet_ms.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.evenjoin.diet_ms.entity.TypeIngredient;
import com.evenjoin.diet_ms.repository.TypeIngredientRepo;
import com.evenjoin.diet_ms.services.interfaces.ITypeIngredientSvc;

@Service
public class TypeIngredientSvc implements ITypeIngredientSvc {
	
	@Autowired
	private TypeIngredientRepo typeIngredientRepo;

	@Override
	public List<TypeIngredient> getTypeIngredients() {
		return typeIngredientRepo.findAll();
	}

	@Override
	public TypeIngredient getTypeIngredient(Long idTypeIngredient) {
		return typeIngredientRepo.findById(idTypeIngredient).orElse(null);
	}

	@Override
	public TypeIngredient addTypeIngredient(TypeIngredient typeIngredient) {
		return typeIngredientRepo.save(typeIngredient);
	}

	@Override
	public void deleteTypeIngredient(Long idTypeIngredient) {
		typeIngredientRepo.deleteById(idTypeIngredient);
	}

}
