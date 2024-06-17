package com.evenjoin.diet_ms.services;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.evenjoin.diet_ms.entity.Mineral;
import com.evenjoin.diet_ms.repository.MineralRepo;
import com.evenjoin.diet_ms.services.interfaces.IMineralSvc;

@Service
public class MineralSvc implements IMineralSvc {

	@Autowired
	private MineralRepo mineralRepo;

	@Override
	@Transactional(readOnly = true)
	public List<Mineral> getMinerals() {
		return mineralRepo.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Mineral getMineral(Long idMineral) {
		return mineralRepo.findById(idMineral).orElse(null);
	}

	@Override
	@Transactional
	public Mineral addMineral(Mineral mineral) {
		return mineralRepo.save(mineral);
	}

	@Override
	@Transactional
	public void deleteMineral(Long idMineral) {
		mineralRepo.deleteById(idMineral);
	}

	@Override
	@Transactional(readOnly = true)
	public Mineral getMineralsByIngredient(Long idIngredient) {
		return mineralRepo.getMineralsByIngredient(idIngredient);
	}

	@Override
	@Transactional(readOnly = true)
	public Object getMineralsByRecipe(Long idRecipe) {
		return mineralRepo.getMineralsByRecipe(idRecipe);
	}

	@Override
	@Transactional(readOnly = true)
	public Object getMineralsByDiet(Long idDiet) {
		return mineralRepo.getMineralsByDiet(idDiet);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Object> getMineralsByDietRange(Date startDate, Date endDate) {
		return mineralRepo.getMineralsByDietRange(startDate, endDate);
	}
}
