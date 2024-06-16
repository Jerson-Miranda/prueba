package com.evenjoin.diet_ms.services;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.evenjoin.diet_ms.entity.Diet;
import com.evenjoin.diet_ms.repository.DietRepo;
import com.evenjoin.diet_ms.services.interfaces.IDietSvc;

@Service
public class DietSvc implements IDietSvc {

	@Autowired
	private DietRepo dietRepo;

	@Override
	@Transactional(readOnly = true)
	public List<Diet> getDiets() {
		return dietRepo.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Diet getDiet(Long idDiet) {
		return dietRepo.findById(idDiet).orElse(null);
	}

	@Override
	@Transactional
	public Diet addDiet(Diet diet) {
		return dietRepo.save(diet);
	}

	@Override
	@Transactional
	public void deleteDiet(Long idDiet) {
		dietRepo.deleteById(idDiet);
	}

	@Override
	@Transactional(readOnly = true)
	public BigDecimal getPriceByDiet(Long idDiet) {
		return dietRepo.getPriceByDiet(idDiet);
	}

	@Override
	@Transactional(readOnly = true)
	public BigDecimal getPriceByDietRange(Date startDate, Date endDate) {
		return dietRepo.getPriceByDietRange(startDate, endDate);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Object> getPricesByDietRange(Date startDate, Date endDate) {
		return dietRepo.getPricesByDietRange(startDate, endDate);
	}

	@Override
	public Integer getTimeByDiet(Long idDiet) {
		return dietRepo.getTimeByDiet(idDiet);
	}
}
