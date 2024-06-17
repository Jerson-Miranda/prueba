package com.evenjoin.diet_ms.services.interfaces;

import java.util.Date;
import java.util.List;
import com.evenjoin.diet_ms.entity.Mineral;

public interface IMineralSvc {

	public List<Mineral> getMinerals();
	public Mineral getMineral(Long idMineral);
	public Mineral addMineral(Mineral mineral);
	public void deleteMineral(Long idMineral);
	public Mineral getMineralsByIngredient(Long idIngredient);
	public Object getMineralsByRecipe(Long idRecipe);
	public Object getMineralsByDiet(Long idDiet);
	public List<Object> getMineralsByDietRange(Date startDate, Date endDate);
	
}
