package com.evenjoin.diet_ms.services.interfaces;

import java.util.List;

import com.evenjoin.diet_ms.entity.Diet;

public interface IDietSvc {

	public List<Diet> getDiets();
	public Diet getDiet(Long idDiet);
	public Diet addDiet(Diet diet);
	public void deleteDiet(Long idDiet);
	
}
