package com.evenjoin.diet_ms.services.interfaces;

import java.util.Date;
import java.util.List;
import com.evenjoin.diet_ms.entity.Vitamin;

public interface IVitaminSvc {

	public List<Vitamin> getVitamins();
	public Vitamin getVitamin(Long idVitamin);
	public Vitamin addVitamin(Vitamin vitamin);
	public void deleteVitamin(Long idVitamin);
	public Vitamin getVitaminsByIngredient(Long idIngredient);
	public Object getVitaminsByRecipe(Long idRecipe);
	public Object getVitaminsByDiet(Long idDiet);
	public List<Object> getVitaminsByDietRange(Date startDate, Date endDate);
	
}
