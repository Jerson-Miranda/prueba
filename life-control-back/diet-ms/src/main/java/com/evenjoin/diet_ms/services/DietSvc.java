package com.evenjoin.diet_ms.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.evenjoin.diet_ms.entity.Diet;
import com.evenjoin.diet_ms.repository.DietRepo;
import com.evenjoin.diet_ms.services.interfaces.IDietSvc;

@Service
public class DietSvc implements IDietSvc {

	@Autowired
	private DietRepo dietRepo;

	@Override
	public List<Diet> getDiets() {
		return dietRepo.findAll();
	}

	@Override
	public Diet getDiet(Long idDiet) {
		return dietRepo.findById(idDiet).orElse(null);
	}

	@Override
	public Diet addDiet(Diet diet) {
		return dietRepo.save(diet);
	}

	@Override
	public void deleteDiet(Long idDiet) {
		dietRepo.deleteById(idDiet);
	}
}
