package com.evenjoin.diet_ms.services.interfaces;

import java.util.List;

import com.evenjoin.diet_ms.entity.NutritionalFact;

public interface INutritionalFactSvc {

	public List<NutritionalFact> getNutritionalFacts();
	public NutritionalFact getNutritionalFact(Long idNutricionalFact);
	public NutritionalFact addNutritionalFact(NutritionalFact nutritionalFact);
	public void deleteNutritionalFact(Long idNutritionalFact);
	
}
