package com.evenjoin.diet_ms.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.evenjoin.diet_ms.repository.DietRepositoryBookRepo;
import com.evenjoin.diet_ms.services.interfaces.IDietRecipeBookSvc;

@Service
public class DietRecipeBookSvc implements IDietRecipeBookSvc {
	
	@Autowired
	private DietRepositoryBookRepo dietRepositoryBookRepo;
	
}
