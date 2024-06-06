package com.evenjoin.diet_ms.services.interfaces;

import java.util.List;

import com.evenjoin.diet_ms.entity.Pantry;

public interface IPantrySvc {

	public List<Pantry> getPantries();
	public Pantry getPantry(Long idPantry);
	public Pantry addPantry(Pantry pantry);
	public void deletePantry(Long idPantry);
	
}
