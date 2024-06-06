package com.evenjoin.diet_ms.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.evenjoin.diet_ms.entity.NutritionalFact;
import com.evenjoin.diet_ms.repository.NutritionalFactRepo;
import com.evenjoin.diet_ms.services.interfaces.INutritionalFactSvc;

@Service
public class NutritionalFactSvc implements INutritionalFactSvc {

	@Autowired
	private NutritionalFactRepo nutritionalFactRepo;

	@Override
	public List<NutritionalFact> getNutritionalFacts() {
		return nutritionalFactRepo.findAll();
	}

	@Override
	public NutritionalFact getNutritionalFact(Long idNutritionalFact) {
		return nutritionalFactRepo.findById(idNutritionalFact).orElse(null);
	}

	@Override
	public NutritionalFact addNutritionalFact(NutritionalFact nutritionalFact) {
		return nutritionalFactRepo.save(nutritionalFact);
	}

	@Override
	public void deleteNutritionalFact(Long idNutritionalFact) {
		nutritionalFactRepo.deleteById(idNutritionalFact);
	}
}
