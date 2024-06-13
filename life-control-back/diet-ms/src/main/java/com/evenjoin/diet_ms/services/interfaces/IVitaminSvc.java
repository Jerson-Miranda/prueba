package com.evenjoin.diet_ms.services.interfaces;

import java.util.List;
import com.evenjoin.diet_ms.entity.Vitamin;

public interface IVitaminSvc {

	public List<Vitamin> getVitamins();
	public Vitamin getVitamin(Long idVitamin);
	public Vitamin addVitamin(Vitamin vitamin);
	public void deleteVitamin(Long idVitamin);
	public Vitamin getVitaminsByIngredient(String barcode);
	
}
