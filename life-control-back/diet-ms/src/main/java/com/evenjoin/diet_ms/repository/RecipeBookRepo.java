package com.evenjoin.diet_ms.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.evenjoin.diet_ms.entity.RecipeBook;

public interface RecipeBookRepo extends JpaRepository<RecipeBook, Long> {

}
