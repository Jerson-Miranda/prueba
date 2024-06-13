package com.evenjoin.diet_ms.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.evenjoin.diet_ms.entity.Micronutrient;
import com.evenjoin.diet_ms.repository.MicronutrientRepo;
import com.evenjoin.diet_ms.services.interfaces.IMicronutrientSvc;

@Service
public class MicronutrientSvc implements IMicronutrientSvc {

	@Autowired
	private MicronutrientRepo micronutrientRepo;
	
	@Override
	@Transactional(readOnly = true)
	public List<Micronutrient> getMicronutrients() {
		return micronutrientRepo.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Micronutrient getMicronutrient(Long idMicronutrient) {
		return micronutrientRepo.findById(idMicronutrient).orElse(null);
	}

	@Override
	@Transactional
	public Micronutrient addMicronutrient(Micronutrient micronutrient) {
		return micronutrientRepo.save(micronutrient);
	}

	@Override
	@Transactional
	public void deleteMicronutrient(Long idMicronutrient) {
		micronutrientRepo.deleteById(idMicronutrient);
	}

	@Override
	public Micronutrient getMicronutrientsByIngredient(String barcode) {
		return micronutrientRepo.getMicronutrientsByIngredient(barcode);
	}

}
