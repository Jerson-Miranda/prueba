package com.evenjoin.diet_ms.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.evenjoin.diet_ms.entity.TypeIngredient;
import com.evenjoin.diet_ms.repository.TypeIngredientRepo;
import com.evenjoin.diet_ms.services.interfaces.ITypeIngredientSvc;

@Service
public class TypeIngredientSvc implements ITypeIngredientSvc {
	
	@Autowired
	private TypeIngredientRepo typeIngredientRepo;

	@Override
	@Transactional(readOnly = true)
	public List<TypeIngredient> getTypeIngredients() {
		return typeIngredientRepo.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public TypeIngredient getTypeIngredient(Long idTypeIngredient) {
		return typeIngredientRepo.findById(idTypeIngredient).orElse(null);
	}

	@Override
	@Transactional
	public TypeIngredient addTypeIngredient(TypeIngredient typeIngredient) {
		return typeIngredientRepo.save(typeIngredient);
	}

	@Override
	@Transactional
	public void deleteTypeIngredient(Long idTypeIngredient) {
		typeIngredientRepo.deleteById(idTypeIngredient);
	}

}
