package com.evenjoin.diet_ms.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.evenjoin.diet_ms.entity.Pantry;
import com.evenjoin.diet_ms.repository.PantryRepo;
import com.evenjoin.diet_ms.services.interfaces.IPantrySvc;

@Service
public class PantrySvc implements IPantrySvc {

	@Autowired
	private PantryRepo pantryRepo;

	@Override
	public List<Pantry> getPantries() {
		return pantryRepo.findAll();
	}

	@Override
	public Pantry getPantry(Long idPantry) {
		return pantryRepo.findById(idPantry).orElse(null);
	}

	@Override
	public Pantry addPantry(Pantry pantry) {
		return pantryRepo.save(pantry);
	}

	@Override
	public void deletePantry(Long idPantry) {
		pantryRepo.deleteById(idPantry);
	}
}
