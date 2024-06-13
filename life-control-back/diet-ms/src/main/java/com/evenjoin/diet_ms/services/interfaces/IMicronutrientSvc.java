package com.evenjoin.diet_ms.services.interfaces;

import java.util.List;
import com.evenjoin.diet_ms.entity.Micronutrient;

public interface IMicronutrientSvc {

	public List<Micronutrient> getMicronutrients();
	public Micronutrient getMicronutrient(Long idMicronutrient);
	public Micronutrient addMicronutrient(Micronutrient micronutrient);
	public void deleteMicronutrient(Long idMicronutrient);
	public Micronutrient getMicronutrientsByIngredient(String barcode);
	
}
