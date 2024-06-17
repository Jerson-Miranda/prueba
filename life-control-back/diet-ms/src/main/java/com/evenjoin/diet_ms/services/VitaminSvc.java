package com.evenjoin.diet_ms.services;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.evenjoin.diet_ms.entity.Vitamin;
import com.evenjoin.diet_ms.repository.VitaminRepo;
import com.evenjoin.diet_ms.services.interfaces.IVitaminSvc;

@Service
public class VitaminSvc implements IVitaminSvc {

	@Autowired
	private VitaminRepo vitaminRepo;

	@Override
	@Transactional(readOnly = true)
	public List<Vitamin> getVitamins() {
		return vitaminRepo.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Vitamin getVitamin(Long idVitamin) {
		return vitaminRepo.findById(idVitamin).orElse(null);
	}

	@Override
	@Transactional
	public Vitamin addVitamin(Vitamin vitamin) {
		return vitaminRepo.save(vitamin);
	}

	@Override
	@Transactional
	public void deleteVitamin(Long idVitamin) {
		vitaminRepo.deleteById(idVitamin);
	}

	@Override
	@Transactional(readOnly = true)
	public Vitamin getVitaminsByIngredient(Long idIngredient) {
		return vitaminRepo.getVitaminsByIngredient(idIngredient);
	}

	@Override
	@Transactional(readOnly = true)
	public Object getVitaminsByRecipe(Long idRecipe) {
		return vitaminRepo.getVitaminsByRecipe(idRecipe);
	}

	@Override
	@Transactional(readOnly = true)
	public Object getVitaminsByDiet(Long idDiet) {
		return vitaminRepo.getVitaminsByDiet(idDiet);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Object> getVitaminsByDietRange(Date startDate, Date endDate) {
		return vitaminRepo.getVitaminsByDietRange(startDate, endDate);
	}
	
}
