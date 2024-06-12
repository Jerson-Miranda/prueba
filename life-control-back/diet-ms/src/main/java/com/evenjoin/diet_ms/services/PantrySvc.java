package com.evenjoin.diet_ms.services;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.evenjoin.diet_ms.entity.Pantry;
import com.evenjoin.diet_ms.repository.PantryRepo;
import com.evenjoin.diet_ms.services.interfaces.IPantrySvc;

@Service
public class PantrySvc implements IPantrySvc {

	@Autowired
	private PantryRepo pantryRepo;

	@Override
	@Transactional(readOnly = true)
	public List<Pantry> getPantries() {
		return pantryRepo.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Pantry getPantry(Long idPantry) {
		return pantryRepo.findById(idPantry).orElse(null);
	}

	@Override
	@Transactional
	public Pantry addPantry(Pantry pantry) {
		return pantryRepo.save(pantry);
	}

	@Override
	@Transactional
	public void deletePantry(Long idPantry) {
		pantryRepo.deleteById(idPantry);
	}

	@Override
	public Date getExpirationDateByIngredient(String barcode) {
		return pantryRepo.getExpirationDateByIngredient(barcode);
	}

	@Override
	public Integer getStockByIngredient(String barcode) {
		return pantryRepo.getStockByIngredient(barcode);
	}
	
}
