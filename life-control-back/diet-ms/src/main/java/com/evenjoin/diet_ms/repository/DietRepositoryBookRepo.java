package com.evenjoin.diet_ms.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.evenjoin.diet_ms.entity.DietRecipeBook;
import com.evenjoin.diet_ms.entity.embedded.DietRecipeBookId;

public interface DietRepositoryBookRepo extends JpaRepository<DietRecipeBook, DietRecipeBookId> {

}
